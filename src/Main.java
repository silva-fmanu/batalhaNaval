import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in);

        String[][] campoJogador = criarCampo();
        String[][] campoAdversario = criarCampo();

        String[][] campoAtacarJogador = criarCampo();//ataca o jogador
        String[][] campoAtacarAdversario = criarCampo();//ataca o adversário


        boolean multiplayer = false; //variavel para validação multiplayer ou não

        for (int i = 0; i == 0; ) { // faz enquanto o jogador não colocar uma resposta valida
            System.out.print("Você deseja jogar:\nm -> multiplayer\ns -> singleplayer(contra máquina)\n\nDigite a sua opção: ");
            char op = ler.next().toLowerCase().charAt(0);//informa a opção

            if (op == 'm') {
                multiplayer = true; // é multiplayer
                i++;//sai do loop
            } else if (op == 's') {
                multiplayer = false;// é singleplayer
                i++;//sai do loop
            } else {
                System.out.println("Opção inválida.");
            }
        }

        boolean automatico = false;//variavel para validação automático ou não

        for (int i = 0; i == 0; ) { // faz enquanto a opção não for válida
            System.out.print("\nJogador, de que modo você deseja alocar os barcos:\na -> automatico\nm -> manual\n\nDigite a sua opção: ");
            char op = ler.next().toLowerCase().charAt(0);// informa a opção

            if (op == 'a') {
                automatico = true;//é automático
                i++;//sai do loop
            } else if (op == 'm') {
                automatico = false;// é manual
                i++;//sai do loop
            } else {
                System.out.println("Opção inválida.");
            }
        }

        boolean dificil = false;//variavél para validação de dificil ou não

        if (!multiplayer) {// se multiplayer for false quer dizer que é singleplayer
            for (int i = 0; i == 0; ) {//escolhe se é facil ou dificil
                System.out.print("\nQual dificuldade de jogo você deseja:\nf -> facil\nd -> dificil\n\nDigite a sua opção: ");
                char op = ler.next().toLowerCase().charAt(0);

                if (op == 'd') {
                    dificil = true;//é dificil
                    i++;//sai do loop
                } else if (op == 'f') {
                    dificil = false;//é facil
                    i++;//sai do loop
                } else {
                    System.out.println("Opção inválida.");
                }
            }
        }

        int[] tamanhos = new int[]{4, 3, 2, 1};//array para o tamanho dos barcos que serão alocados

        if (automatico) {//alocação automatica
            autoAlocar(campoJogador, tamanhos);//função que aloca automaticamente, passa a matriz do campo jogador e tamanho dos barcos
            System.out.println("\nTabuleiro do Jogador:");
            mostraTabuleiro(campoJogador);// função que mostra o tabuleiro com os barcos
        } else { //aloca manualmente
            manualAlocar(campoJogador, tamanhos);//função que aloca manualmente passando campo jogador e tamanho dos barcos
        }

        boolean automaticoAd = false;//variavel que define se o adversário(dentro do multiplayer) que alocar manualmente ou automaticamente

        if (multiplayer) {// é multiplayer, pede pro adversario como ele quer alocar o barco, e faz o mesmo do jogador
            System.out.println("\nJogador adversário, de que modo você deseja alocar os barcos:");
            for (int i = 0; i == 0; ) {
                System.out.print("a -> automatico\nm -> manual\n\nDigite a sua opção: ");
                char op = ler.next().toLowerCase().charAt(0);

                if (op == 'a') {
                    automaticoAd = true;
                    i++;
                } else if (op == 'm') {
                    automaticoAd = false;
                    i++;
                } else {
                    System.out.println("Opção inválida.");
                }
            }
            if (automaticoAd) {
                autoAlocar(campoAdversario, tamanhos);
                System.out.println("Tabuleiro do Jogador Adversário:");
                mostraTabuleiro(campoAdversario);
            } else {
                manualAlocar(campoAdversario, tamanhos);
            }
        }

        int ganhou = 0;//varivel para testar se um dos jogadores ganhou
        int barcosAdversarios = 20;// quantidade total de barcos do adversario
        int barcosJogador = 20;//quantidade total de barcos do jogador

        if (multiplayer) {// é multiplayer
            do {
                System.out.println("\nAtaque do Jogador:");
                mostraTabuleiro(campoAtacarAdversario); //mostra o tabuleiro do jogador adversario para o jogador atacar
                System.out.println("♒ = água | 💣 = errou um barco | ⛵ = acertou um barco | " + barcosAdversarios + " = barcos restantes");

                //esse for é pra ter certeza que o jogador vai tentar acertar um lugar que ainda não foi tentado
                for (int i = 0; i == 0; ) {
                    System.out.println("Faça o ataque:");
                    int[] cordenadas = cordenadas();//função que pede a posição de linha e coluna por meio de um arrar posi 0 linha posi 1 coluna
                    if (checarLivre(cordenadas[0], cordenadas[1], 'h', campoAtacarAdversario, 1)) {//checa pra ver se o jogador já acertou esse lugar
                        campoAtacarAdversario = ataque(cordenadas[0], cordenadas[1], campoAdversario, campoAtacarAdversario);// função que marca no mapa a posição atacada com barco ou bomba
                        if (acertou(cordenadas[0], cordenadas[1], campoAdversario)) {
                            barcosAdversarios--;
                            System.out.println("Você acertou!");
                            mostraTabuleiro(campoAtacarAdversario);
                            i--;
                        }
                        i++;
                    } else {
                        System.out.print("\nVocê já tentou acertar esse lugar! Tente outra posição.");
                    }
                }

                if (barcosAdversarios == 0) {
                    ganhou = 1;
                    break;
                }
                mostraTabuleiro(campoAtacarAdversario);
                System.out.println("♒ = água | 💣 = errou um barco | ⛵ = acertou um barco | " + barcosAdversarios + " = barcos restantes");


                System.out.println("\nAtaque do Adversário:");

                mostraTabuleiro(campoAtacarJogador);
                System.out.println("♒ = água | 💣 = errou um barco | ⛵ = acertou um barco | " + barcosJogador + " = barcos restantes");

                //esse for é pra ter certeza que o jogador vai tentar acertar um lugar que ainda não foi tentado
                for (int i = 0; i == 0; ) {
                    System.out.println("Faça o ataque:");
                    int[] cordenadas = cordenadas();

                    if (checarLivre(cordenadas[0], cordenadas[1], 'h', campoAtacarJogador, 1)) {//checa pra ver se o jogador já acertou esse lugar
                        campoAtacarJogador = ataque(cordenadas[0], cordenadas[1], campoJogador, campoAtacarJogador);
                        if (acertou(cordenadas[0], cordenadas[1], campoJogador)) {
                            barcosJogador--;
                            System.out.println("Você acertou!");
                            mostraTabuleiro(campoAtacarJogador);
                            i--;
                        }
                        i++;
                    } else {
                        System.out.println("Você já tentou acertar esse lugar! Tente outra posição.");
                    }
                }

                if (barcosJogador == 0) {
                    ganhou = 2;
                    break;
                }

                mostraTabuleiro(campoAtacarJogador);
                System.out.println("♒ = água | 💣 = errou um barco | ⛵ = acertou um barco | " + barcosJogador + " = barcos restantes");


            } while (ganhou == 0);
        } else { //jogador no modo solo, contra máquina
            Random gerar = new Random();
            autoAlocar(campoAdversario, tamanhos);
            if (!dificil) { // jogador contra uma máquina não inteligente
                do {
                    System.out.println("\nCampo Adversário:");
                    mostraTabuleiro(campoAtacarAdversario);
                    System.out.println("♒ = água | 💣 = errou um barco | ⛵ = acertou um barco | " + barcosAdversarios + " = barcos restantes");
                    System.out.println("O adversário acertou " + (20 - barcosJogador) + " dos seus barcos.");
                    System.out.println("\nAtaque do Jogador:");

                    //esse for é pra ter certeza que o jogador vai tentar acertar um lugar que ainda não foi tentado
                    for (int i = 0; i == 0; ) {
                        System.out.println("Faça o ataque:");
                        int[] cordenadas = cordenadas();
                        if (checarLivre(cordenadas[0], cordenadas[1], 'h', campoAtacarAdversario, 1)) {//checa pra ver se o jogador já acertou esse lugar
                            campoAtacarAdversario = ataque(cordenadas[0], cordenadas[1], campoAdversario, campoAtacarAdversario);
                            if (acertou(cordenadas[0], cordenadas[1], campoAdversario)) {//a função retorna true se acertou
                                barcosAdversarios--; // diminui o numero de barcos
                                System.out.println("Você acertou!");
                                mostraTabuleiro(campoAtacarAdversario);//mostra o tabuleiro com a posição marcada
                                i--;//joga de novo
                            }
                            i++;//sai do loop caso errou
                        } else {
                            System.out.print("\nVocê já tentou acertar esse lugar! Tente outra posição.");
                        }
                    }

                    if (barcosAdversarios == 0) {// se os barcos chegarem a 0 vai somar um no ganhou e vai sair do while
                        ganhou = 1;
                        break;//vai sair do loop sem ter que esperar o resto do codigo ser lido
                    }
                    mostraTabuleiro(campoAtacarAdversario);//mostra o tabuleiro com a posição que foi informada marcada
                    System.out.println("♒ = água | 💣 = errou um barco | ⛵ = acertou um barco | " + barcosAdversarios + " = barcos restantes");


                    System.out.println("\nAtaque da Máquina:");
                    for (int i = 0; i == 0; ) {
                        int linha = gerar.nextInt(0, 10);// gera uma linha aleatoria
                        int coluna = gerar.nextInt(0, 10);// gera uma coluna aleatoria
                        if (checarLivre(linha, coluna, 'h', campoAtacarJogador, 1)) {//h é pq como e de um em um não importa a direção, tamanho 1, pq é de um em um
                            campoAtacarJogador = ataque(linha, coluna, campoJogador, campoAtacarJogador);
                            if (acertou(linha, coluna, campoJogador)) {
                                System.out.println("Acertou jogue de novo!");
                                barcosJogador--;
                                System.out.println("Máquina Acertou!");
                                mostraTabuleiro(campoAtacarAdversario);
                                i--;
                            }
                            i++;
                        }
                    }
                    if (barcosJogador == 0) {
                        ganhou = 2;
                        break;
                    }
                    mostraTabuleiro(campoAtacarJogador);

                } while (ganhou == 0);
            } else { // jogador contra uma máquina inteligente que tenta acertar mais
                boolean acerto = false;
                int linha = 0;
                int coluna = 0;
                boolean xdireita = false;
                boolean xesquerda = false;
                boolean ycima = false;
                boolean ybaixo = false;
                do {
                    System.out.println("\nCampo Adversário:");
                    mostraTabuleiro(campoAtacarAdversario);
                    System.out.println("♒ = água | 💣 = errou um barco | ⛵ = acertou um barco | " + barcosAdversarios + " = barcos restantes");
                    System.out.println("O adversário acertou " + (20 - barcosJogador) + " dos seus barcos.");// mostra aquantidade de barcos que ele acertou
                    System.out.println("\nAtaque do Jogador:");

                    //esse for é pra ter certeza que o jogador vai tentar acertar um lugar que ainda não foi tentado
                    for (int i = 0; i == 0; ) {
                        System.out.println("Faça o ataque:");
                        int[] cordenadas = cordenadas();//linha e coluna
                        if (checarLivre(cordenadas[0], cordenadas[1], 'h', campoAtacarAdversario, 1)) {//checa pra ver se o jogador já acertou esse lugar
                            campoAtacarAdversario = ataque(cordenadas[0], cordenadas[1], campoAdversario, campoAtacarAdversario);
                            if (acertou(cordenadas[0], cordenadas[1], campoAdversario)) {
                                barcosAdversarios--;
                                System.out.println("Você acertou!");
                                mostraTabuleiro(campoAtacarAdversario);
                                i--;
                            }
                            i++;
                        } else {
                            System.out.print("\nVocê já tentou acertar esse lugar! Tente outra posição.");
                        }
                    }

                    if (barcosAdversarios == 0) {
                        ganhou = 1;
                        break;
                    }
                    mostraTabuleiro(campoAtacarAdversario);
                    System.out.println("♒ = água | 💣 = errou um barco | ⛵ = acertou um barco | " + barcosAdversarios + " = barcos restantes");


                    System.out.println("\nAtaque da Máquina:");

                    /*
                    acertar um lugar aleatório, se for barco, acertar em algum outro lugar próximo, se errou, quantos erros. se já sabe que o barco
                    é horizontal, não precisa testar na vertical. ver se ta dentro do campo do jogo checar as bordas do jogo. se acertou tem que
                    continuar na mesma direção, se errou, tem checar pra ver se na outra direção já tinha errado, senão, tentar acertar na outra direção
                     */


                    // inteligência da máquina, pra não acertar de modo 100% aleatório
                    for (int i = 0; i == 0; ) {//checar se acertou um lugar que não tinha antes
                        if (acerto) {//caso ele ja tenha acertado o barquinho um vez
                            if (xdireita || xesquerda) {// xdireira ou x esquerda tem que ser true pra cair aqui, pq se os dois for falase quer dizer que ele vertical
                                if (coluna < 9 && xdireita && campoAtacarJogador[linha][(coluna + 1)].equals("♒")) {
                                    coluna++;
                                    if (!campoAtacarJogador[linha][coluna].equals("💣")) {
                                        campoAtacarJogador = ataque(linha, coluna, campoJogador, campoAtacarJogador);
                                        if (acertou(linha, coluna, campoAtacarJogador)) {
                                            barcosJogador--;
                                            ycima = false;
                                            ybaixo = false;
                                        } else {
                                            xdireita = false;
                                            coluna--;
                                            i++;
                                        }
                                    } else {
                                        xdireita = false;
                                        coluna--;
                                    }
                                } else if (coluna > 0 && xesquerda) {
                                    coluna--;
                                    boolean livreAcerto = false;
                                    if (campoAtacarJogador[linha][coluna].equals("♒")) {
                                        livreAcerto = true;
                                    }
                                    if (!campoAtacarJogador[linha][coluna].equals("💣")) {
                                        campoAtacarJogador = ataque(linha, coluna, campoJogador, campoAtacarJogador);
                                        if (acertou(linha, coluna, campoAtacarJogador)) {
                                            if (livreAcerto) {
                                                barcosJogador--;
                                                ycima = false;
                                                ybaixo = false;
                                            }
                                            xdireita = false;
                                        } else {
                                            xesquerda = false;
                                            coluna++;
                                            i++;
                                        }
                                    } else {
                                        xesquerda = false;

                                    }
                                } else {
                                    xdireita = false;
                                    xesquerda = false;
                                }
                            } else if (ycima || ybaixo) {
                                if (linha > 0 && ycima) {
                                    linha--;
                                    if (campoAtacarJogador[linha][coluna].equals("♒")) {
                                        if (!campoAtacarJogador[linha][coluna].equals("💣")) {
                                            campoAtacarJogador = ataque(linha, coluna, campoJogador, campoAtacarJogador);
                                            if (acertou(linha, coluna, campoAtacarJogador)) {
                                                barcosJogador--;
                                                xdireita = false;
                                                xesquerda = false;
                                            } else {
                                                i++;
                                                ycima = false;
                                            }
                                        } else {
                                            ycima = false;
                                        }
                                    } else {
                                        ycima = false;
                                    }
                                } else if (linha < 9 && ybaixo) {
                                    linha++;
                                    boolean livreAcerto = false;
                                    if (campoAtacarJogador[linha][coluna].equals("♒")) {
                                        livreAcerto = true;
                                    }
                                    if (!campoAtacarJogador[linha][coluna].equals("💣")) {
                                        campoAtacarJogador = ataque(linha, coluna, campoJogador, campoAtacarJogador);
                                        if (acertou(linha, coluna, campoAtacarJogador)) {
                                            if (livreAcerto) {
                                                barcosJogador--;
                                                xdireita = false;
                                                xesquerda = false;
                                            }
                                            ycima = false;
                                        } else {
                                            i++;
                                            ybaixo = false;
                                            ycima = false;
                                        }
                                    } else {
                                        ybaixo = false;
                                    }
                                } else {
                                    ycima = false;
                                    ybaixo = false;
                                }
                            } else {
                                acerto = false;
                            }
                        } else {//primeira não tem noção nenhuma de onde jogar
                            linha = gerar.nextInt(0, 10);
                            coluna = gerar.nextInt(0, 10);
                            if (checarLivre(linha, coluna, 'h', campoAtacarJogador, 1)) {
                                campoAtacarJogador = ataque(linha, coluna, campoJogador, campoAtacarJogador);
                                if (acertou(linha, coluna, campoJogador)) {
                                    barcosJogador--;
                                    acerto = true;
                                    xdireita = true;
                                    xesquerda = true;
                                    ycima = true;
                                    ybaixo = true;
                                } else {
                                    acerto = false;
                                    i++;
                                }
                            }
                        }
                    }
                    if (barcosJogador == 0) {
                        ganhou = 2;
                        break;
                    }
                    mostraTabuleiro(campoAtacarJogador);

                } while (ganhou == 0);
            }
        }
        System.out.println("\nCampo do Jogador:");
        mostraTabuleiro(campoAtacarJogador);
        System.out.println("\nCampo do Adversário:");
        mostraTabuleiro(campoAtacarAdversario);
        if (ganhou == 1) {
            System.out.println("\nO Jogador foi o vencedor da rodada!");
        } else {
            System.out.println("\nO Adversário foi o vencedor da rodada!");
        }

    }

    static String[][] criarCampo() {
        String[][] campo = new String[10][10];
        for (int i = 0; i < campo.length; i++) {
            for (int j = 0; j < campo[i].length; j++) {
                campo[i][j] = "♒";
            }
        }
        return campo;
    }

    static void mostraTabuleiro(String[][] campo) {
        System.out.println("|| A| B| C| D | E| F| G | H| I| J|");
        for (int i = 0; i < campo.length; i++) {
            System.out.print(i + "|");
            for (int j = 0; j < campo[i].length; j++) {
                System.out.print(campo[i][j] + "|");
            }
            System.out.println();
        }
    }

    static int pegarNumero(char op) {
        int num;
        switch (op) {
            case 'a' -> num = 0;
            case 'b' -> num = 1;
            case 'c' -> num = 2;
            case 'd' -> num = 3;
            case 'e' -> num = 4;
            case 'f' -> num = 5;
            case 'g' -> num = 6;
            case 'h' -> num = 7;
            case 'i' -> num = 8;
            case 'j' -> num = 9;
            default -> num = 10;
        }

        return num;
    }

    static boolean checarLivre(int linha, int coluna, char direcao, String[][] campo, int tamanho) {
        if (direcao == 'h') {
            if (coluna + tamanho > campo[0].length) {
                return false;
            }

            for (int i = coluna; i < coluna + tamanho; i++) {
                if (!campo[linha][i].equals("♒")) {
                    return false;
                }
            }
        } else { //se não for  h vai ser v
            if (linha + tamanho > campo.length) { //checar pra ver se não passa pra fora do campo, mas agora na vertical
                return false;
            }

            for (int i = linha; i < linha + tamanho; i++) { //checa pra ver se é tudo água
                if (!campo[i][coluna].equals("♒")) {
                    return false;
                }
            }
        }

        return true; // se tiver tudo livre, retorna como verdade, ou seja, é tudo água então pode colocar o barquinho
    }

    static String[][] alocarBarco(int linha, int coluna, char direcao, String[][] campo, int tamanho) {
        if (direcao == 'h') {
            for (int i = coluna; i < coluna + tamanho; i++) {
                campo[linha][i] = "⛵";
            }
            return campo;
        } else {
            for (int i = linha; i < linha + tamanho; i++) {
                campo[i][coluna] = "⛵";
            }
            return campo;
        }
    }

    static void autoAlocar(String[][] campoJogador, int[] tamanhos) { // tamanhos [4,3,2,1]
        Random gerar = new Random();
        boolean alocou;
        for (int i = 0; i < tamanhos.length; i++) {// os tamanhos do barco
            for (int j = i; j >= 0; ) { // quantidade que deve repetir, por tamanho de barco
                do {
                    alocou = false;
                    int gerarDirecao = gerar.nextInt(1, 100);
                    char opDirecao = ' ';

                    if (gerarDirecao % 2 == 0) {
                        opDirecao = 'h';
                    } else {
                        opDirecao = 'v';
                    }

                    int linha = gerar.nextInt(0, 10);
                    int coluna = gerar.nextInt(0, 10);

                    if (checarLivre(linha, coluna, opDirecao, campoJogador, tamanhos[i])) {
                        alocarBarco(linha, coluna, opDirecao, campoJogador, tamanhos[i]);
                        alocou = true;
                    }
                } while (!alocou);//enquanto nao alocar
                j--;
            }
        }
    }

    static void manualAlocar(String[][] campo, int[] tamanhos) {
        Scanner ler = new Scanner(System.in);
        for (int i = 0; i < tamanhos.length; i++) {
            for (int j = i; j >= 0; ) {
                mostraTabuleiro(campo);
                System.out.print((j + 1) + "x Barco grande (" + tamanhos[i] + " espaços):\nh - horizontal\nv - vertical\nOpção: ");
                char opDirecao = 'a';
                for (int a = 0; a == 0; ) {
                    if (ler.hasNextInt()) {
                        System.out.println("Não é letra!");
                        ler.nextInt();//sair do loop
                    } else {
                        String op = ler.next();
                        if (op.length() == 1) {//colocar so uma letra
                            opDirecao = op.toLowerCase().charAt(0);
                            if (opDirecao == 'h' || opDirecao == 'v') {
                                a++;
                            } else {
                                System.out.println("Opção inválida.");
                            }
                        } else {
                            System.out.println("Opção inválida.");
                        }
                    }
                }

                int[] cordenadas = cordenadas();
                if (checarLivre(cordenadas[0], cordenadas[1], opDirecao, campo, tamanhos[i])) {
                    System.out.println("Ta livre");
                    campo = alocarBarco(cordenadas[0], cordenadas[1], opDirecao, campo, tamanhos[i]);
                    j--;
                } else {
                    System.out.println("não ta livre, tente de novo");
                }
            }
        }
    }

    static String[][] ataque(int linha, int coluna, String[][] campoReferencia, String[][] campoMostra) {
        if (campoReferencia[linha][coluna].equals("⛵")) {
            campoMostra[linha][coluna] = "⛵";
        } else {
            campoMostra[linha][coluna] = "💣";
        }
        return campoMostra;
    }

    static int[] cordenadas() {
        Scanner ler = new Scanner(System.in);

        int[] cordenadas = new int[2];

        for (int i = 0; i == 0; ) {
            for (int j = 0; j == 0; ) {
                System.out.print("\nLinha: ");
                if (ler.hasNextInt()) {
                    cordenadas[0] = ler.nextInt();
                    j++;
                } else {
                    System.out.println("Não é um número!");
                    ler.next();
                }
            }

            if (cordenadas[0] >= 0 && cordenadas[0] <= 9) {
                i++;
            } else {
                System.out.println("Opção inválida.");
            }
        }


        for (int i = 0; i == 0; ) {
            System.out.print("\nColuna: ");
            if (ler.hasNextInt()) {
                System.out.println("Não é letra!");
                ler.nextInt();
            } else {
                String charColuna = ler.next();
                if (charColuna.length() == 1) {
                    int coluna = pegarNumero(charColuna.toLowerCase().charAt(0));
                    if (coluna != 10) {
                        cordenadas[1] = coluna;
                        i++;
                    } else {
                        System.out.println("Opção inválida.");
                    }
                } else {
                    System.out.println("Opção inválida.");
                }

            }
        }

        return cordenadas;
    }

    static boolean acertou(int linha, int coluna, String[][] campo) {
        boolean acertou = false;
        if (campo[linha][coluna].equals("⛵")) {
            acertou = true;
        }
        return acertou;
    }
}
