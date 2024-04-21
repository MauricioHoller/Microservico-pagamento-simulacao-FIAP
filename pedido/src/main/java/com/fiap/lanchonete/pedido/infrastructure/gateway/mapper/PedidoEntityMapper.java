package com.fiap.lanchonete.pedido.infrastructure.gateway.mapper;

import com.fiap.lanchonete.pedido.domain.entity.Pedido;
import com.fiap.lanchonete.pedido.infrastructure.persistence.entity.PedidoEntity;

public class PedidoEntityMapper {
	ProdutoEntityMapper mapperProduct = new ProdutoEntityMapper();
	public PedidoEntity paraPedidoEntity(Pedido PedidoObjectDomain) {
	return new PedidoEntity(PedidoObjectDomain.getId() ,PedidoObjectDomain.getListaProdutos(),PedidoObjectDomain.getStatusPedido(), PedidoObjectDomain.getStatusPagamento(), PedidoObjectDomain.getValorTotal());

	}
	
	public Pedido paraObjetoDominio(PedidoEntity pedidoEntity) {
		return new Pedido(pedidoEntity.getId(), pedidoEntity.getListaProdutosPedido(),
				pedidoEntity.getStatusPedido(), pedidoEntity.getStatusPagamento(), pedidoEntity.getValorTotal());
	}
}