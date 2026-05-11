package main.FIPE.Apps;

import java.io.IOException;
import java.util.Scanner;

    public class ConsoleMenu {

        private final Scanner scanner = new Scanner(System.in);
        private final AuctionScrapperApp auctionScrapperRun = new AuctionScrapperApp();
        private final SearchService SearchService = new SearchService();

        public void iniciar() {
            boolean executando = true;

            while (executando) {
                mostrarMenu();
                String opcao = scanner.nextLine();

                switch (opcao) {
                    case "1" -> executarScrape();
                    case "2" -> executarPesquisa();
                    case "0" -> {
                        executando = false;
                        scanner.close();
                        System.out.println("Saindo...");
                    }
                    default -> System.out.println("Opção inválida.");
                }
            }
        }

        private void mostrarMenu() {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Fazer scrape de carros do leilão");
            System.out.println("2 - Fazer uma pesquisa");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
        }

        private void executarScrape() {
            try {
                auctionScrapperRun.run();
            } catch (IOException e) {
                System.out.println("Erro ao executar scrape: " + e.getMessage());
            }
        }

        private void executarPesquisa() {
            System.out.print("Digite a marca do carro: ");
            String marca = scanner.nextLine();
            System.out.print("Digite o modelo do carro: ");
            String modelo = scanner.nextLine();
            System.out.print("Digite o ano do carro: ");
            String ano = scanner.nextLine();
            try {
                SearchService.SearchCar( marca, modelo, ano);
            } catch (IOException e) {
                System.out.println("Erro ao procurar veículo: " + e.getMessage());
                scanner.close();
            }
        }
    }
