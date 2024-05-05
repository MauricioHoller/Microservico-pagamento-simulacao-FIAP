package com.fiap.lanchonete.pagamento.infrastructure.gateway.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.fiap.lanchonete.pagamento.domain.entity.Categoria;
import com.fiap.lanchonete.pagamento.domain.entity.Produto;
import com.fiap.lanchonete.pagamento.infrastructure.persistence.entity.ProdutoEntity;

class ProdutoEntityMapperTest {

    @Test
    void testParaProdutoEntity() {
        ProdutoEntityMapper mapper = new ProdutoEntityMapper();
        String nome = "Hambúrguer";
        String descricao = "Delicioso hambúrguer";
        Categoria categoria = Categoria.Lanche;
        BigDecimal valor = BigDecimal.valueOf(10.0);

        Produto produto = new Produto(categoria, nome, descricao, valor);

        ProdutoEntity produtoEntity = mapper.paraProdutoEntity(produto);

        assertEquals(nome, produtoEntity.getNome());
        assertEquals(descricao, produtoEntity.getDescricao());
        assertEquals(categoria, produtoEntity.getCategoria());
        assertEquals(valor, produtoEntity.getValor());
    }

    @Test
    void testParaObjetoDominio() {
        ProdutoEntityMapper mapper = new ProdutoEntityMapper();
        String nome = "Hambúrguer";
        String descricao = "Delicioso hambúrguer";
        Categoria categoria = Categoria.Lanche;
        BigDecimal valor = BigDecimal.valueOf(10.0);

        ProdutoEntity produtoEntity = new ProdutoEntity(categoria, nome, descricao, valor);

        Produto produto = mapper.paraObjetoDominio(produtoEntity);

        assertEquals(nome, produto.getNome());
        assertEquals(descricao, produto.getDescricao());
        assertEquals(categoria, produto.getCategoria());
        assertEquals(valor, produto.getValor());
    }
}