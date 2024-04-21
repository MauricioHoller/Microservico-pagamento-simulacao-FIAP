package com.fiap.lanchonete.pagamento.infrastructure.controller;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.lanchonete.pagamento.application.usecases.PedidoUseCases;
import com.fiap.lanchonete.pagamento.application.usecases.exceptions.PedidoNaoEncontradoException;
import com.fiap.lanchonete.pagamento.domain.entity.Pedido;
import com.fiap.lanchonete.pagamento.domain.entity.StatusPagamento;
import com.fiap.lanchonete.pagamento.domain.entity.StatusPedido;
import com.fiap.lanchonete.pagamento.infrastructure.mapper.PedidoRequestMapper;
import com.fiap.lanchonete.pagamento.infrastructure.requestsdto.PedidoPagamentoResponse;
import com.fiap.lanchonete.pagamento.infrastructure.requestsdto.PedidoResponse;

@RestController
@RequestMapping("api/v1/pedido")
public class PedidoController {

	private static final String PEDIDO_EXCHANGE_1 = "pagamento-exchange";
	private static final String PEDIDO_PAGAMENTO_ROUTING_KEY = "pagamento-para-pedido-routing-key";

	private final PedidoUseCases pedidoUseCases;
	private final PedidoRequestMapper mapper;
	private final RabbitTemplate template;

	public PedidoController(PedidoUseCases pedidoUseCases, PedidoRequestMapper mapper, RabbitTemplate template) {
		this.pedidoUseCases = pedidoUseCases;
		this.mapper = mapper;
		this.template = template;
	}

	@GetMapping
	public List<PedidoResponse> buscaPedidos() {
		return pedidoUseCases.buscaPedidos().stream().map(mapper::paraResponse).toList();
	};

	@GetMapping("{id}")
	public ResponseEntity<PedidoResponse> buscaPedidosPorId(@PathVariable("id") int id) {
		try {
			return new ResponseEntity<>(mapper.paraResponse(pedidoUseCases.buscaPedidoId(id)), HttpStatus.OK);
		} catch (PedidoNaoEncontradoException e) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
	};

	@GetMapping("pagamento/{id}")
	public ResponseEntity<PedidoPagamentoResponse> buscaPedidosPagamento(@PathVariable Integer id) {
		try {
			return new ResponseEntity<>(mapper.paraResponseDTO(pedidoUseCases.buscaPedidoId(id)), HttpStatus.OK);

		} catch (PedidoNaoEncontradoException e) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
	};

	@GetMapping("/status")
	public List<PedidoResponse> buscaPedidosPorStatus(@RequestBody StatusPedido status) {
		return pedidoUseCases.buscaPedidosPorStatus(status).stream().map(mapper::paraResponse).toList();
	};

	// WEBHOOK
	@PostMapping("pagamento/mercadopago/{topic}/{id}")
	ResponseEntity<String> webHookMercadoPagoSimulator(@PathVariable("topic") String topic, @PathVariable("id") Integer id) {
		Pedido pedidoAtualizado = pedidoUseCases.atualizaPedidoPagamento(topic, id);
		template.convertAndSend(PEDIDO_EXCHANGE_1, PEDIDO_PAGAMENTO_ROUTING_KEY, pedidoAtualizado);
		if (pedidoAtualizado.getStatusPagamento().equals(StatusPagamento.Pago)) {
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("Pedido pago com sucesso!");
		} else {
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("Pedido Cancelado!");

		}
	}

}