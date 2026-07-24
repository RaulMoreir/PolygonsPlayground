package main.FIPE.Apps;

import main.FIPE.models.BrandsEnum;

import java.io.IOException;
import java.util.Scanner;

    public class ConsoleMenu {

        private final Scanner scanner = new Scanner(System.in);
        private final AuctionScrapperApp auctionScrapperRun = new AuctionScrapperApp();
        private final SearchService SearchService = new SearchService(false);

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
        private void escolherQualBancoDeDados(){

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
                auctionScrapperRun.runJsonDatabase();
            } catch (IOException e) {
                System.out.println("Erro ao executar scrape: " + e.getMessage());
            }
        }

        private boolean isInvalidBrand(String marca){
            BrandsEnum brand = BrandsEnum.fromString(marca.toUpperCase());
            return brand == BrandsEnum.UNDEFINED;

        }

        private void executarPesquisa() {
            String marca; String modelo; String ano;

            // MARCA
            while (true) {
                System.out.print("Digite a marca do carro: ");
                marca = scanner.nextLine();

                if (isInvalidBrand(marca)) {
                    System.out.println("Parâmetro inválido, marca não existe.");
                    continue;
                }
                break;
            }

            // MODELO
            System.out.print("Digite o modelo do carro: ");
            modelo = scanner.nextLine();

            // ANO
            while (true) {
                System.out.print("Digite o ano do carro: ");
                ano = scanner.nextLine();

                if (!ano.isBlank() && !ano.matches("\\d{4}")) {
                    System.out.println("Parâmetro inválido, digite exatamente 4 números ou deixe vazio.");
                    continue;
                }
                break;
            }

            try {
                SearchService.SearchCar(marca, modelo, ano);
            } catch (IOException e) {
                System.out.println("Erro ao procurar veículo: " + e.getMessage());
            }
    }

    }
