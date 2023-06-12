// Este trecho de código importa as classes e pacotes necessários para a implementação da interface gráfica, manipulação de eventos, operações de entrada e saída, e manipulação de listas.
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


// Esta é uma classe abstrata chamada "Jogador" que define as características básicas de um jogador. 
// Ela possui seis atributos (nome, posição, clube, preço, médiaDoJogador e últimaPontuação) e um construtor para inicializar esses atributos. 
// Também possui um método abstrato "informaçõesDoJogador()" que será implementado nas classes derivadas.
abstract class Jogador {
    String nome;
    String posição;
    String clube;
    double preço;
    String médiaDoJogador;
    String últimaPontuação;

    Jogador(String nome, String posição, String clube, double preço, String médiaDoJogador, String últimaPontuação) {
        this.nome = nome;
        this.posição = posição;
        this.clube = clube;
        this.preço = preço;
        this.médiaDoJogador = médiaDoJogador;
        this.últimaPontuação = últimaPontuação;
    }

    abstract void informaçõesDoJogador();
}
// Essa é uma classe derivada da classe abstrata "Jogador". Ela estende a classe "Jogador" e implementa o método abstrato "informaçõesDoJogador()". 
// Essa implementação apenas imprime uma mensagem na saída padrão.
class JogadorDeFutebol extends Jogador {
    JogadorDeFutebol(String nome, String posição, String clube, double preço, String médiaDoJogador, String últimaPontuação) {
        super(nome, posição, clube, preço, médiaDoJogador, últimaPontuação);
    }

    @Override
    void informaçõesDoJogador() {
        System.out.println("Informações do Jogador:");
    }
}
// Esta é uma classe de exceção personalizada que estende a classe "Exception". Ela permite criar exceções personalizadas com uma mensagem específica.
class ExceçãoPersonalizada extends Exception {
    ExceçãoPersonalizada(String mensagem) {
        super(mensagem);
    }
}
// Esta linha declara uma classe chamada "CartolaFC" que herda da classe "JFrame" e implementa a interface "ActionListener". 
// Isso significa que a classe "CartolaFC" é uma janela de aplicativo que pode responder a eventos de ação.
public class CartolaFC extends JFrame implements ActionListener {

    // Essas linhas declaram algumas variáveis utilizadas pela classe "CartolaFC". 
    // São declaradas uma lista de botões de posição, um painel para exibir os jogadores, uma lista de jogadores comprados, 
    // um valor de saldo inicializado como 100.0 e um rótulo para exibir o saldo.
    private List<JButton> botõesDePosição;
    private JPanel painelDeJogadores;
    private List<Jogador> jogadoresComprados;
    private double saldo = 100.0;
    private JLabel saldoLabel;

    // Este é o construtor da classe "CartolaFC". Ele define o título da janela como "Cartola FC", define a operação de fechamento padrão para encerrar o 
    // aplicativo quando a janela for fechada, define o tamanho da janela como 400x300 pixels e define o layout da janela como BorderLayout.
    public CartolaFC() {
        setTitle("Cartola FC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Essas linhas criam uma lista de botões de posição e adicionam botões a essa lista. 
        // Cada botão é criado com um rótulo que representa uma posição de jogador no time de futebol na formacao 4-3-3.
        botõesDePosição = new ArrayList<>();
        botõesDePosição.add(new JButton("Goleiro"));
        botõesDePosição.add(new JButton("Zagueiro 1"));
        botõesDePosição.add(new JButton("Zagueiro 2"));
        botõesDePosição.add(new JButton("Lateral 1"));
        botõesDePosição.add(new JButton("Lateral 2"));
        botõesDePosição.add(new JButton("Meia 1"));
        botõesDePosição.add(new JButton("Meia 2"));
        botõesDePosição.add(new JButton("Meia 3"));
        botõesDePosição.add(new JButton("Atacante 1"));
        botõesDePosição.add(new JButton("Atacante 2"));
        botõesDePosição.add(new JButton("Atacante 3"));

        // Essas linhas criam um painel para exibir os botões. O layout do painel é definido como BoxLayout na orientação vertical. 
        // Um loop é usado para adicionar cada botão da lista de botões de posição ao painel de botões. 
        // Além disso, um ouvinte de ação é adicionado a cada botão, definindo seu tamanho preferido como 100x30 pixels.
        JPanel painelDeBotões = new JPanel();
        painelDeBotões.setLayout(new BoxLayout(painelDeBotões, BoxLayout.Y_AXIS));
        for (JButton botão : botõesDePosição) {
            botão.addActionListener(this);
            botão.setPreferredSize(new Dimension(100, 30));
            painelDeBotões.add(botão);
        }

        // Essas linhas criam um novo botão chamado "Salvar" e o adicionam ao painel de botões. Também é adicionado um ouvinte de ação ao botão.
        JButton botãoSalvar = new JButton("Salvar");
        botãoSalvar.addActionListener(this);
        painelDeBotões.add(botãoSalvar);

        // Esta linha cria um rótulo de texto que exibe o saldo atual e o adiciona ao painel de botões.
        saldoLabel = new JLabel("Saldo: R$ " + saldo);
        painelDeBotões.add(saldoLabel);

        // Esta linha adiciona o painel de botões à janela "CartolaFC" na posição oeste (lado esquerdo).
        add(painelDeBotões, BorderLayout.WEST);

        // Essas linhas criam um painel de rolagem que contém um painel para exibir os jogadores. 
        // O layout do painel de jogadores é definido como BoxLayout na orientação vertical. 
        // Em seguida, o painel de jogadores é adicionado ao painel de rolagem e o painel de rolagem é adicionado à janela "CartolaFC" na posição central.
        painelDeJogadores = new JPanel();
        painelDeJogadores.setLayout(new BoxLayout(painelDeJogadores, BoxLayout.Y_AXIS));
        JScrollPane painelDeRolagem = new JScrollPane(painelDeJogadores);
        add(painelDeRolagem, BorderLayout.CENTER);

        // Essas linhas inicializam a lista de jogadores comprados como uma nova lista vazia e tornam a janela "CartolaFC" visível.
        jogadoresComprados = new ArrayList<>();
        setVisible(true);
    }

    // Essa é a função principal do programa. Ela inicia a aplicação Swing ao criar uma instância da classe "CartolaFC" dentro de um objeto Runnable e passá-lo 
    // para o método invokeLater() da classe SwingUtilities. Isso garante que a criação e exibição da janela ocorram na thread de despacho de eventos do Swing.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CartolaFC();
            }
        });
    }

    // Esse método é um listener de eventos que é acionado quando um botão é clicado. Ele começa obtendo o botão que foi clicado, 
    // convertendo o evento ActionEvent para um objeto JButton e armazenando-o em uma variável botãoClicado. 
    // Em seguida, o texto do botão é obtido através do método getText() e armazenado em uma variável textoDoBotão.
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton botãoClicado = (JButton) e.getSource();
        String textoDoBotão = botãoClicado.getText();

        // Aqui, é feita uma verificação do texto do botão. Se o texto for "Salvar", o método salvarJogadoresComprados() é chamado. 
        // Caso contrário, o texto do botão é dividido por um espaço em branco usando o método split(" "), e a primeira parte (a posição) é armazenada na variável posição.
        // Em seguida, o método filtrarJogadoresPorPosição(posição) é chamado para obter uma lista de jogadores filtrados por posição.
        // O painel de jogadores (painelDeJogadores) é limpo usando o método removeAll() para remover todos os componentes anteriores.
        // Então, começa um loop for que itera sobre a lista de jogadores filtrados. Para cada jogador, são extraídas informações individuais (nome, posição, clube, preço, média e última pontuação) 
        // a partir da linha de dados do jogador, que é dividida por vírgulas usando o método split(","). Essas informações são armazenadas em variáveis correspondentes.
        // Em seguida, é criado um objeto JogadorDeFutebol com as informações extraídas. Um novo painel (painelDoJogador) é criado e configurado com um layout BoxLayout vertical e uma borda vazia.
        // Etiquetas JLabel são criadas para cada informação do jogador, exibindo o texto correspondente.
        // Um botão botãoComprar é criado com o texto "Comprar". 
        // Um novo listener de eventos é adicionado ao botão usando uma classe anônima que substitui o método actionPerformed(). Quando o botão é clicado, esse listener é acionado.
        // Dentro do listener, é verificado se o saldo é suficiente para comprar o jogador (saldo é uma variável que armazena o saldo atual). 
        // Se for suficiente, o jogador é adicionado à lista jogadoresComprados, o valor do jogador é subtraído do saldo, o saldo é atualizado e exibido na etiqueta saldoLabel, 
        // uma mensagem de diálogo é exibida informando que o jogador foi comprado e o botão clicado é desativado.
        // Caso o saldo seja insuficiente, uma mensagem de diálogo é exibida informando que o saldo é insuficiente para comprar o jogador.
        // As etiquetas e o botão são adicionados ao painel do jogador (painelDoJogador), e esse painel é adicionado ao painel de jogadores (painelDeJogadores) no final do loop.
        // Após o loop, os métodos revalidate() e repaint() são chamados no painel de jogadores para atualizar a exibição dos componentes.
        if (textoDoBotão.equals("Salvar")) {
            salvarJogadoresComprados();
        } else {
            String posição = textoDoBotão.split(" ")[0];
            List<String> jogadores = filtrarJogadoresPorPosição(posição);

            painelDeJogadores.removeAll();
            for (String jogador : jogadores) {
                String[] dados = jogador.split(",");
                String nomeJogador = dados[0];
                String posiçãoJogador = dados[1];
                String clubeJogador = dados[2];
                double preçoJogador = Double.parseDouble(dados[3]);
                String médiaJogador = dados[4];
                String últimaPontuaçãoJogador = dados[5];

                Jogador jogadorDeFutebol = new JogadorDeFutebol(nomeJogador, posiçãoJogador, clubeJogador, preçoJogador, médiaJogador, últimaPontuaçãoJogador);
                JPanel painelDoJogador = new JPanel();
                painelDoJogador.setLayout(new BoxLayout(painelDoJogador, BoxLayout.Y_AXIS));
                painelDoJogador.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JLabel etiquetaNome = new JLabel("Nome: " + nomeJogador);
                JLabel etiquetaPosição = new JLabel("Posição: " + posiçãoJogador);
                JLabel etiquetaClube = new JLabel("Clube: " + clubeJogador);
                JLabel etiquetaPreço = new JLabel("Preço: " + preçoJogador);
                JLabel etiquetaMédia = new JLabel("Média: " + médiaJogador);
                JLabel etiquetaÚltimaPontuação = new JLabel("Última Pontuação: " + últimaPontuaçãoJogador);

                JButton botãoComprar = new JButton("Comprar");
                botãoComprar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (saldo >= jogadorDeFutebol.preço) {
                            jogadoresComprados.add(jogadorDeFutebol);
                            saldo -= jogadorDeFutebol.preço;
                            saldoLabel.setText("Saldo: R$ " + String.format("%.2f", saldo)); // Atualiza o texto do saldoLabel
                            JOptionPane.showMessageDialog(CartolaFC.this, "Jogador comprado: " + nomeJogador);
                            botãoClicado.setEnabled(false);
                        } else {
                            JOptionPane.showMessageDialog(CartolaFC.this, "Saldo insuficiente para comprar este jogador.");
                        }
                    }
                });

                painelDoJogador.add(etiquetaNome);
                painelDoJogador.add(etiquetaPosição);
                painelDoJogador.add(etiquetaClube);
                painelDoJogador.add(etiquetaPreço);
                painelDoJogador.add(etiquetaMédia);
                painelDoJogador.add(etiquetaÚltimaPontuação);
                painelDoJogador.add(botãoComprar);

                painelDeJogadores.add(painelDoJogador);
            }

            painelDeJogadores.revalidate();
            painelDeJogadores.repaint();
        }
    }

    // Esse método recebe uma posição como parâmetro e retorna uma lista de jogadores filtrados por essa posição. 
    // Ele lê os dados de um arquivo chamado "jogadoresEditados.csv", onde cada linha contém informações separadas por vírgulas sobre um jogador.
    // O método percorre cada linha do arquivo, divide a linha em um array de strings usando a vírgula como separador e verifica se o segundo elemento (posição) corresponde à posição fornecida. 
    // Se for o caso, a linha inteira é adicionada à lista de jogadores.
    private List<String> filtrarJogadoresPorPosição(String posição) {
        List<String> jogadores = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("jogadoresEditados.csv"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 6 && dados[1].equalsIgnoreCase(posição)) {
                    jogadores.add(linha);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jogadores;
    }

    // Este método é responsável por salvar os jogadores comprados em um arquivo chamado "jogadoresComprados.csv". 
    // Ele itera sobre a lista de jogadores comprados e grava as informações de cada jogador (nome, posição, clube e preço) como uma linha no arquivo. 
    // Cada jogador é separado por vírgulas, e o método write() é usado para escrever a linha no arquivo. O método newLine() é chamado para inserir uma nova linha após cada jogador.
    private void salvarJogadoresComprados() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("jogadoresComprados.csv"))) {
            for (Jogador jogador : jogadoresComprados) {
                String linha = jogador.nome + "," + jogador.posição + "," + jogador.clube + "," + jogador.preço;
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
