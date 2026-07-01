<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <title>Alunos - Focus</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<header class="topo">
    <div class="marca">Focus</div>

    <nav class="menu">
        <a href="${pageContext.request.contextPath}/servicos">Início</a>
        <a href="${pageContext.request.contextPath}/alunos">Alunos</a>
        <a href="${pageContext.request.contextPath}/planos">Planos</a>
        <a href="${pageContext.request.contextPath}/treinos">Treinos</a>
        <a href="${pageContext.request.contextPath}/logout">Sair</a>
    </nav>
</header>

<main class="conteudo">
    <h1>Alunos</h1>

    <c:if test="${not empty erro}">
        <p class="mensagem-erro">${erro}</p>
    </c:if>

    <form class="formulario" action="${pageContext.request.contextPath}/alunos" method="post">
        <input type="hidden" name="id" value="${aluno.id}">

        <label for="nome">Nome</label>
        <input type="text" id="nome" name="nome" value="${aluno.nome}" required>

        <label for="cpf">CPF</label>
        <input type="text" id="cpf" name="cpf" value="${aluno.cpf}" inputmode="numeric" maxlength="14" data-mask="cpf" placeholder="000.000.000-00" required>

        <label for="dataNascimento">Data de nascimento</label>
        <input type="date" id="dataNascimento" name="dataNascimento" value="${aluno.dataNascimento}">

        <label for="endereco">Endereço</label>
        <input type="text" id="endereco" name="endereco" value="${aluno.endereco}">

        <label for="telefone">Telefone</label>
        <input type="text" id="telefone" name="telefone" value="${aluno.telefone}" inputmode="numeric" maxlength="15" data-mask="telefone" placeholder="(55) 99999-9999">

        <label for="idPlano">Plano</label>
        <select id="idPlano" name="idPlano" required>
            <option value="">Selecione</option>

            <c:forEach var="plano" items="${planos}">
                <option value="${plano.id}" ${aluno.idPlano == plano.id ? "selected" : ""}>
                        ${plano.nome}
                </option>
            </c:forEach>
        </select>

        <button type="submit">Salvar</button>

        <c:if test="${not empty aluno}">
            <a class="botao-secundario" href="${pageContext.request.contextPath}/alunos">Cancelar edição</a>
        </c:if>
    </form>

    <table class="tabela">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>CPF</th>
            <th>Telefone</th>
            <th>Plano</th>
            <th>Ações</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${alunos}">
            <tr>
                <td>${item.id}</td>
                <td>${item.nome}</td>
                <td>${item.cpf}</td>
                <td>${item.telefone}</td>
                <td>${item.nomePlano}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/alunos?acao=editar&id=${item.id}">Editar</a>
                    |
                    <a href="${pageContext.request.contextPath}/alunos?acao=excluir&id=${item.id}"
                       onclick="return confirm('Deseja realmente excluir este aluno?');">
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
