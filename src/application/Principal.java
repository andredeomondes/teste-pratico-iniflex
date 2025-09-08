package application;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import entities.Pessoa;
import entities.Funcionario;

public class Principal {
    public static void main(String[] args) {

        // Formatação Geral --------------------------------------------------------------------------------------------

        // Formatando data conforme o exemplo: 07/09/2025 --------------------------------------------------------------
        DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Formatando números decimais conforme o exemplo: R$1.500,00 --------------------------------------------------
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale("pt", "BR"));
        dfs.setDecimalSeparator(',');
        dfs.setGroupingSeparator('.');
        DecimalFormat formatadorSalario = new DecimalFormat("#,##0.00", dfs);

        // 3.1 – Inserir todos os funcionários, na mesma ordem e informações da tabela ---------------------------------
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        // 3.2 – Remover o funcionário “João” da lista -----------------------------------------------------------------
        funcionarios.removeIf(f -> f.getNome().equalsIgnoreCase("João"));

        // 3.3 – Imprimir todos os funcionários com todas suas informações, sendo que:
        // • informação de data deve ser exibido no formato dd/mm/aaaa;
        //• informação de valor numérico deve ser exibida no formatado com separador de milhar como ponto e decimal como vírgula.
        for (Funcionario f : funcionarios) {
            String data = formatadorData.format(f.getDataNascimento());
            String salario = formatadorSalario.format(f.getSalario());
            System.out.println(f.getNome() + " | " + data + " | " + f.getFuncao() + " | R$ " + salario);
        }

        // 3.4 – Os funcionários receberam 10% de aumento de salário, atualizar a lista de funcionários com novo valor -
        for (Funcionario f : funcionarios) {
            f.aumentarSalarioPercentual(new BigDecimal("0.10"));
        }

        // 3.5 – Agrupar os funcionários por função em um MAP, sendo a chave -------------------------------------------
        // a “função” e o valor a “lista de funcionários” --------------------------------------------------------------

        Map<String, List<Funcionario>> funcionariosPorFuncao =
                funcionarios.stream().collect(Collectors.groupingBy(Funcionario::getFuncao));

        // 3.6 – Imprimir os funcionários, agrupados por função --------------------------------------------------------
        System.out.println("\n---- Funcionários agrupados por função ----");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println(funcao + ":");
            lista.forEach(f -> System.out.println("  - " + f.getNome()));
        });
        System.out.println();

        // 3.8 – Imprimir os funcionários que fazem aniversário no mês 10 e 12 -----------------------------------------
        System.out.println("---- Aniversariantes de Outubro e Dezembro ----");
        funcionarios.stream()
                .filter(f -> f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12)
                .forEach(f -> System.out.println(f.getNome() + " | " + f.getDataNascimento().format(formatadorData)));
        System.out.println();

        // 3.9 – Imprimir o funcionário com a maior idade, exibir os atributos: nome e idade ---------------------------
        Funcionario maisVelho = funcionarios.stream().max(Comparator.comparingInt(Pessoa::getIdade)).orElse(null);
        if (maisVelho != null) {
            System.out.println("---- Funcionário mais velho ----");
            System.out.println(maisVelho.getNome() + " | " + maisVelho.getIdade() + " anos");
        }
        System.out.println();

        // 3.10 – Imprimir a lista de funcionários por ordem alfabética ------------------------------------------------
        System.out.println("---- Funcionários em ordem alfabética ----");
        funcionarios.stream().sorted(Comparator.comparing(Funcionario::getNome, String.CASE_INSENSITIVE_ORDER))
                .forEach(f -> System.out.println(" - " + f.getNome()));
        System.out.println();

        // 3.11 - Imprimir o total dos salários dos funcionários -------------------------------------------------------
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("---- Total dos salários ----");
        System.out.println("R$ " + formatadorSalario.format(totalSalarios));
        System.out.println();

        // 3.12 – Imprimir quantos salários mínimos ganha cada funcionário, --------------------------------------------
        // considerando que o salário mínimo é R$1212.00. --------------------------------------------------------------
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("---- Quantidade de salários mínimos de cada funcionário ----");
        for (Funcionario f : funcionarios) {
            BigDecimal qtdSalarios = f.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            System.out.println(f.getNome() + " | " + qtdSalarios + " salários mínimos");
        }

    }

}

