<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <title>Focus</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<header class="topo">
    <div class="marca">Focus</div>
</header>

<main class="pagina-inicial">
    <section class="sobre">
        <h1>A evolução do seu treino.</h1>
        <h2>Sobre nós</h2>

        <p>
            Bem-vindo ao Focus, uma plataforma para organizar alunos, planos e treinos
            de forma simples, rápida e objetiva.
        </p>

        <p>
            O sistema ajuda a academia a acompanhar sua rotina operacional, controlar
            cadastros e visualizar indicadores importantes em tempo real.
        </p>
    </section>

    <section class="login">
        <h2>Login</h2>

        <form action="login" method="post">
            <label for="email">E-mail</label>
            <input type="email" id="email" name="email" autocomplete="email" required>

            <label for="senha">Senha</label>
            <input type="password" id="senha" name="senha" autocomplete="current-password" required>

            <button type="submit">Entrar</button>
        </form>

        <p class="mensagem-erro">${erro}</p>
    </section>
</main>

<footer class="rodape">
    <p>Focus Performance</p>
    <p>Contato: contato@focus.com</p>
</footer>

</body>
</html>
