<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <title>Planos - Focus</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<header class="topo">
    <div class="marca">Focus</div>

    <nav class="menu">
        <a href="servicos">Início</a>
        <a href="alunos">Alunos</a>
        <a href="planos">Planos</a>
        <a href="treinos">Treinos</a>
        <a href="logout">Sair</a>
    </nav>
</header>

<main class="conteudo">
    <h1>Planos</h1>

    <c:if test="${not empty erro}">
        <p class="mensagem-erro">${erro}</p>
    </c:if>

    <form class="formulario" action="planos" method="post">
        <input type="hidden" name="id" value="${plano.id}">

        <label for="nome">Nome</label>
        <input type="text" id="nome" name="nome" value="${plano.nome}" required>

        <label for="descricao">Descrição</label>
        <textarea id="descricao" name="descricao" rows="3">${plano.descricao}</textarea>

        <label for="valor">Valor</label>
        <input type="text" id="valor" name="valor" value="${plano.valor}" inputmode="decimal" data-mask="valor" placeholder="149,90" required>

        <label class="checkbox">
            <input type="checkbox" name="ativo" ${plano == null || plano.ativo ? "checked" : ""}>
            Ativo
        </label>

        <button type="submit">Salvar</button>

        <c:if test="${not empty plano}">
            <a class="botao-secundario" href="planos">Cancelar edição</a>
        </c:if>
    </form>

    <table class="tabela">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Descrição</th>
            <th>Valor</th>
            <th>Ativo</th>
            <th>Ações</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${planos}">
            <tr>
                <td>${item.id}</td>
                <td>${item.nome}</td>
                <td>${item.descricao}</td>
                <td>R$ ${item.valor}</td>
                <td>${item.ativo ? "Sim" : "Não"}</td>
                <td>
                    <a href="planos?acao=editar&id=${item.id}">Editar</a>
                    |
                    <a href="planos?acao=excluir&id=${item.id}"
                       onclick="return confirm('Deseja realmente excluir este plano?');">
                        Excluir
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</main>

<script src="${pageContext.request.contextPath}/js/mascaras.js"></script>
</body>
</html>
