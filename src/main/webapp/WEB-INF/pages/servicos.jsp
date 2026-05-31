<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <title>Serviços - Focus</title>
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
    <h1>Painel Focus</h1>

    <p>
        Esta tela apresenta um resumo operacional da academia com base nos dados
        cadastrados no sistema.
    </p>

    <section class="cards">
        <article class="card">
            <h2>Alunos</h2>
            <p class="numero-dashboard">${resumo.totalAlunos}</p>
            <a class="botao-link" href="alunos">Gerenciar alunos</a>
        </article>

        <article class="card">
            <h2>Planos ativos</h2>
            <p class="numero-dashboard">${resumo.totalPlanosAtivos}</p>
            <a class="botao-link" href="planos">Gerenciar planos</a>
        </article>

        <article class="card">
            <h2>Treinos ativos</h2>
            <p class="numero-dashboard">${resumo.totalTreinosAtivos}</p>
            <a class="botao-link" href="treinos">Gerenciar treinos</a>
        </article>
    </section>

    <section class="painel-relatorio">
        <h2>Alunos por plano</h2>

        <table class="tabela">
            <thead>
            <tr>
                <th>Plano</th>
                <th>Quantidade de alunos</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${alunosPorPlano}">
                <tr>
                    <td>${item.nomePlano}</td>
                    <td>${item.quantidadeAlunos}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </section>
</main>

</body>
</html>
