package com.fiap.lanchonete.pagamento.infrastructure.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.lanchonete.pagamento.domain.entity.StatusPedido;
import com.fiap.lanchonete.pagamento.infrastructure.persistence.entity.PedidoEntity;


public interface PedidoRepository  extends JpaRepository<PedidoEntity, Integer>{
	
	List<PedidoEntity> findAllByStatusPedidoOrderById(StatusPedido status);
	
}
