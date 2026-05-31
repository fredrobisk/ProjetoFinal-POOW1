function somenteNumeros(valor) {
    return valor.replace(/\D/g, "");
}

function aplicarMascaraCpf(campo) {
    let valor = somenteNumeros(campo.value).slice(0, 11);

    valor = valor.replace(/(\d{3})(\d)/, "$1.$2");
    valor = valor.replace(/(\d{3})(\d)/, "$1.$2");
    valor = valor.replace(/(\d{3})(\d{1,2})$/, "$1-$2");

    campo.value = valor;
}

function aplicarMascaraTelefone(campo) {
    let valor = somenteNumeros(campo.value).slice(0, 11);

    if (valor.length <= 10) {
        valor = valor.replace(/(\d{2})(\d)/, "($1) $2");
        valor = valor.replace(/(\d{4})(\d)/, "$1-$2");
    } else {
        valor = valor.replace(/(\d{2})(\d)/, "($1) $2");
        valor = valor.replace(/(\d{5})(\d)/, "$1-$2");
    }

    campo.value = valor;
}

function aplicarMascaraValor(campo) {
    let valor = campo.value.replace(/[^\d,.]/g, "");
    valor = valor.replace(".", ",");

    const partes = valor.split(",");
    const reais = somenteNumeros(partes[0]).slice(0, 7);
    const centavos = partes.length > 1 ? somenteNumeros(partes[1]).slice(0, 2) : "";

    campo.value = centavos ? `${reais},${centavos}` : reais;
}

document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll("[data-mask='cpf']").forEach(function (campo) {
        aplicarMascaraCpf(campo);
        campo.addEventListener("input", function () {
            aplicarMascaraCpf(campo);
        });
    });

    document.querySelectorAll("[data-mask='telefone']").forEach(function (campo) {
        aplicarMascaraTelefone(campo);
        campo.addEventListener("input", function () {
            aplicarMascaraTelefone(campo);
        });
    });

    document.querySelectorAll("[data-mask='valor']").forEach(function (campo) {
        aplicarMascaraValor(campo);
        campo.addEventListener("input", function () {
            aplicarMascaraValor(campo);
        });
    });
});
