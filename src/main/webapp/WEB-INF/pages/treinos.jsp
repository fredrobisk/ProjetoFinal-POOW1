<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <title>Treinos - Focus</title>
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
    <h1>Treinos</h1>

    <c:if test="${not empty erro}">
        <p class="mensagem-erro">${erro}</p>
    </c:if>

    <form class="formulario" action="${pageContext.request.contextPath}/treinos" method="post">
        <input type="hidden" name="id" value="${treino.id}">

        <label for="idAluno">Aluno</label>
        <select id="idAluno" name="idAluno" required>
            <option value="">Selecione</option>

            <c:forEach var="aluno" items="${alunos}">
                <option value="${aluno.id}" ${treino.idAluno == aluno.id ? "selected" : ""}>
                        ${aluno.nome}
                </option>
            </c:forEach>
        </select>

        <label for="nome">Nome do treino</label>
        <input type="text" id="nome" name="nome" value="${treino.nome}" required>

        <label for="diaSemana">Dia da semana</label>
        <select id="diaSemana" name="diaSemana">
            <option value="">Selecione</option>
            <option value="Segunda-feira" ${treino.diaSemana == "Segunda-feira" ? "selected" : ""}>Segunda-feira</option>
            <option value="Terça-feira" ${treino.diaSemana == "Terça-feira" ? "selected" : ""}>Terça-feira</option>
            <option value="Quarta-feira" ${treino.diaSemana == "Quarta-feira" ? "selected" : ""}>Quarta-feira</option>
            <option value="Quinta-feira" ${treino.diaSemana == "Quinta-feira" ? "selected" : ""}>Quinta-feira</option>
            <option value="Sexta-feira" ${treino.diaSemana == "Sexta-feira" ? "selected" : ""}>Sexta-feira</option>
            <option value="Sábado" ${treino.diaSemana == "Sábado" ? "selected" : ""}>Sábado</option>
            <option value="Domingo" ${treino.diaSemana == "Domingo" ? "selected" : ""}>Domingo</option>
        </select>

        <label for="descricao">Descrição</label>
        <textarea id="descricao" name="descricao" rows="5" required>${treino.descricao}</textarea>

        <label class="checkbox">
            <input type="checkbox" name="ativo" ${treino == null || treino.ativo ? "checked" : ""}>
            Ativo
        </label>

        <button type="submit">Salvar</button>

        <c:if test="${not empty treino}">
            <a class="botao-secundario" href="${pageContext.request.contextPath}/treinos">Cancelar edição</a>
        </c:if>
    </form>

    <table class="tabela">
        <thead>
        <tr>
            <th>ID</th>
            <th>Aluno</th>
            <th>Treino</th>
            <th>Descrição</th>
            <th>Dia</th>
            <th>Ativo</th>
            <th>Ações</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${treinos}">
            <tr>
                <td>${item.id}</td>
                <td>${item.nomeAluno}</td>
                <td>${item.nome}</td>
                <td>${item.descricao}</td>
                <td>${item.diaSemana}</td>
                <td>${item.ativo ? "Sim" : "Não"}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/treinos?acao=editar&id=${item.id}">Editar</a>
                    |
                    <a href="${pageContext.request.contextPath}/treinos?acao=excluir&id=${item.id}"
                       onclick="return confirm('Deseja realmente excluir este treino?');">
                        Excluir
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</main>

</body>
</html>
