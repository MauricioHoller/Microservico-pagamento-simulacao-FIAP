# language: pt

Funcionalidade: Produto

  Cenario: Busca Pedido
    Quando efeturar uma requisicao para Buscar todos os produtos
    Entao  deve retornar uma lista com os produtos cadastrados
    
  Cenario: Buscar Produtos por Categoria Lanche
	Quando efetuar uma requisicao para Buscar todos os produtos por categoria
	Entao deve retornar uma lista com os produtos cadastrados da categoria
	    
  Cenario: Criar Produto sem ser repetido
	Quando efetuar requisicao de criacao de um novo produto
	Entao O Produto é criado com sucesso
	
	Cenario: Criar Produto repetido
	Quando efetuar requisicao de criacao de um novo produto
	Entao retorna conflito com a string Produto já cadastrado
	
	Cenario: Atualizar Produto
	Quando efetuar requisicao de atualizar um  produto
	Entao retorna a string Produto atualizado com sucesso com Status 200
	
	Cenario: Atualizar Produto não existente
	Quando efetuar requisicao de atualizar um  produto
	Entao retorna a string Produto não cadastrado com Status 400
	