package com.company;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
public class Main {

    public static void main(String[] args) {
        Game user = new Game();
        Game.init();
    }

    static class Game {
        static String[][] database = new String[51][51];
        static Scanner scanner = new Scanner(System.in);
        static int themeCounter = 0;

        public static void showMenu(){
            System.out.println("----------------");
            System.out.println("Escolha o que deseja fazer:");
            System.out.println("1. Gerenciar Temas");
            System.out.println("2. Gerenciar Palavras");
            System.out.println("3. Jogar");
            System.out.println("4. Sair");
        }

        public static void showThemeActions(){
            System.out.println("Gerenciando Temas: ");
            System.out.println("1. Cadastrar Tema");
            System.out.println("2. Excluir Tema");
            System.out.println("3. Buscar Tema");
        }

        public static void themeActions(){
            char optionTheme = '\n';
            optionTheme = scanner.next().charAt(0);
            switch (optionTheme){
                case '1':
                    System.out.println("Digite o tema: ");
                    String theme = scanner.next();
                    int counter = 0;
                    boolean a = false;
                    while(counter < themeCounter){
                        if(database[counter][0].equals(theme)){
                            a = true;
                        }
                        counter++;
                    }
                    if (a) {
                        System.out.println("Este tema já existe");
                        break;
                    }
                    database[themeCounter][0] = theme;
                    themeCounter++;
                    break;
                case '2':
                    System.out.println("Aperte o número equivalente para excluir: ");
                    for (int i = 0; i < themeCounter; i++) {
                        if (database[i][0] != null) System.out.println(i + ". " + database[i][0]);
                    }
                    int position = scanner.nextInt();
                    if (database[position][1] == null){
                        System.out.println("Não foi possível excluir o tema. Verifique se existem palavras cadastradas nesse tema.");
                        break;
                    }
                    database[position][0] = null;
                    break;
                case '3':
                    System.out.println("Digite o tema que deseja buscar: ");
                    String searchWord = scanner.next();
                    counter = 0;
                    int result = 0;
                    while(counter < themeCounter){
                        if(database[counter][0].equals(searchWord)){
                            result = 1;
                        }
                        counter++;
                    }
                    if(result == 1){
                        System.out.println("Este tema existe");
                        break;
                    } else {
                        System.out.println("Este tema não existe");
                        break;
                    }
                default:
                    System.out.println("Tecla invalida, tente novamente!");
            }
        }

        public static void showWordActions(){
            System.out.println("Gerenciando Palavras: ");
            System.out.println("1. Cadastrar Palavras");
            System.out.println("2. Excluir Palavras");
            System.out.println("3. Buscar Palavras");
            System.out.println("4. Listar Palavras");
        }

        public static void wordActions(){
            char optionTheme = '\n';
            optionTheme = scanner.next().charAt(0);
            switch (optionTheme){
                case '1':
                    System.out.println("Digite o tema que deseja adicionar uma palavra: ");
                    String theme = scanner.next();
                    int counter = 0;
                    int result = 0;
                    int index = -1;
                    while(counter < themeCounter){
                        if(database[counter][0].equals(theme)){
                            index = counter;
                        }
                        counter++;
                    }

                    System.out.println("Digite a palavra: ");
                    if (index != -1) {
                        String word = scanner.next();
                        int lastPosition = 1;
                        while(database[index][lastPosition] != null) lastPosition++;
                        database[index][lastPosition] = word;
                        break;
                    }
                    break;
                case '2':
                    System.out.println("Digite a palavra que deseja exluir: ");
                    String searchWord = scanner.next();
                    counter = 0;
                    int isWord = -1;
                    while( counter < themeCounter){
                        isWord = Arrays.asList(database[counter]).indexOf(searchWord);
                        if (isWord != -1) {
                            System.out.println("Deletei: " + database[counter][isWord]);
                            database[counter][isWord] = null;
                            break;
                        }
                        counter++;
                    }
                    if (isWord == -1){
                        System.out.println("Palavra não encontrada");
                    }
                    break;
                case '3':
                    System.out.println("Digite a palavra que deseja buscar: ");
                    searchWord = scanner.next();
                    counter = 0;
                    isWord = -1;
                    while( counter < themeCounter){
                        boolean b = Arrays.asList(database[counter]).contains(searchWord);
                        if (b) {
                            isWord = counter;
                            counter = themeCounter;
                            break;
                        }
                        counter++;
                    }
                    if (isWord != -1){
                        System.out.println("Palavra encontrada no tema " + database[isWord][0]);
                    } else {
                        System.out.println("Palavra não encontrada");
                    }
                    break;
                case '4':
                    System.out.println("Digite o numero do tema que deseja listar as palavras: ");
                    for (int i = 0; i < themeCounter; i++) {
                        if (database[i][0] != null) System.out.println(i + ". " + database[i][0]);
                    }
                    index = scanner.nextInt();
                    int i = 1;
                    while(database[index][i] != null){
                        System.out.println(database[index][i]);
                        i++;
                    }
                    break;
                default:
                    System.out.println("Tecla invalida, tente novamente!");
            }
        }

        public static void showGameActions(){
            System.out.println("Selecione um tema para jogar: ");
            for (int i = 0; i < themeCounter; i++) {
                if (database[i][0] != null) System.out.println(i + ". " + database[i][0]);
            }
        }

        public static void gameActions(){
            Random aleatorio = new Random();
            int playMore = 1;

            while(playMore == 1){
                showGameActions();
                int position = scanner.nextInt();
                if(database[position][1] == null){
                    System.out.println("Não existem palavras neste tema");
                    return;
                } else {
                    int valor = 0;
                    String theme = "";
                    do {
                        valor = aleatorio.nextInt(50) + 1;
                        theme = database[position][valor];
                    }while(database[position][valor] == null);

                    String letrasUsadas = "";
                    String palavraAdivinhada = "";
                    int MAX_ERROS = 5;
                    int tentativas = 0;
                    System.out.println("Palavra: " + theme);

                    for (int i = 0; i < theme.length(); i++) {
                        palavraAdivinhada += "_";

                        while(tentativas < MAX_ERROS){
                            System.out.println("Escolha uma letra: ");
                            char letraTentada = scanner.next().charAt(0);

                            if(letrasUsadas.indexOf(letraTentada) >= 0){
                                System.out.println("Tente outra letra!");
                            } else {
                                letrasUsadas += letraTentada;

                                if(theme.indexOf(letraTentada) >= 0){
                                    palavraAdivinhada = "";

                                    for (int j = 0; j < theme.length(); j++) {
                                        palavraAdivinhada = letrasUsadas.indexOf(theme.charAt(j)) >= 0 ? String.valueOf(theme.charAt(j)) : "_";
                                    }

                                    if(palavraAdivinhada.contains("_")){
                                        System.out.println("Letra correta");
                                    } else {
                                        System.out.println("Parabéns! Você acertou a palavra! Deseja jogar novamente?");
                                        System.out.println("Se sim, aperte 1. Caso não queria aperte qualquer outro número");
                                        playMore = scanner.nextInt();
                                        if(playMore != 1) return;
                                        tentativas = MAX_ERROS;
                                    }
                                } else {
                                    tentativas++;
                                    System.out.println("Você errou, tentativas: " + tentativas);
                                }
                            }
                        }

                    }
                }
            }
        }

        public static void init(){
            char option = '\n';
            System.out.println("Bem vindo ao jogo da forca!");
            do {
                showMenu();
                option = scanner.next().charAt(0);
                switch (option){
                    case '1':
                        showThemeActions();
                        themeActions();
                        break;
                    case '2':
                        showWordActions();
                        wordActions();
                        break;
                    case '3':
                        gameActions();
                        break;
                    case '4':
                        System.out.println("Obrigado por jogar, volte sempre!");
                        break;
                    default:
                        System.out.println("Tecla invalida, tente novamente!");
                }
            }while(option != '4');
        }
    }
}