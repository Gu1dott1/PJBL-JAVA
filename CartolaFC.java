import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


abstract class Jogador {
    String nome;
    String posição;
    Clube clube;
    double preço;
    String médiaDoJogador;
    String últimaPontuação;

    Jogador(String nome, String posição, Clube clube, double preço, String médiaDoJogador, String últimaPontuação) {
        this.nome = nome;
        this.posição = posição;
        this.clube = clube;
        this.preço = preço;
        this.médiaDoJogador = médiaDoJogador;
        this.últimaPontuação = últimaPontuação;
    }

    abstract void informaçõesDoJogador();
}

class Clube {
    private String nome;

    public Clube(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

class JogadorDeFutebol extends Jogador {
    JogadorDeFutebol(String nome, String posição, Clube clube, double preço, String médiaDoJogador, String últimaPontuação) {
        super(nome, posição, clube, preço, médiaDoJogador, últimaPontuação);
    }

    @Override
    void informaçõesDoJogador() {
        System.out.println("Informações do Jogador de Futebol:");
        System.out.println("Nome: " + nome);
        System.out.println("Posição: " + posição);
        System.out.println("Clube: " + clube);
        System.out.println("Preço: " + preço);
        System.out.println("Média do Jogador: " + médiaDoJogador);
        System.out.println("Última Pontuação: " + últimaPontuação);
    }
}

class Treinador extends Jogador {
    Treinador(String nome, String posição, Clube clube, double preço, String médiaDoJogador, String últimaPontuação) {
        super(nome, posição, clube, preço, médiaDoJogador, últimaPontuação);
    }

    @Override
    void informaçõesDoJogador() {
        System.out.println("Informações do Treinador:");
        System.out.println("Nome: " + nome);
        System.out.println("Posição: " + posição);
        System.out.println("Clube: " + clube);
        System.out.println("Preço: " + preço);
        System.out.println("Média do Treinador: " + médiaDoJogador);
        System.out.println("Última Pontuação: " + últimaPontuação);
    }
}

class ExceçãoPersonalizada extends Exception {
    ExceçãoPersonalizada(String mensagem) {
        super(mensagem);
    }
}

public class CartolaFC extends JFrame implements ActionListener {

    private List<JButton> botõesDePosição;
    private JPanel painelDeJogadores;
    private List<Jogador> jogadoresComprados;
    private double saldo = 100.0;
    private JLabel saldoLabel;

    public CartolaFC() {
        setTitle("Cartola FC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

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
        botõesDePosição.add(new JButton("Treinador"));

        JPanel painelDeBotões = new JPanel();
        painelDeBotões.setLayout(new BoxLayout(painelDeBotões, BoxLayout.Y_AXIS));
        for (JButton botão : botõesDePosição) {
            botão.addActionListener(this);
            botão.setPreferredSize(new Dimension(100, 30));
            painelDeBotões.add(botão);
        }

        JButton botãoSalvar = new JButton("Salvar");
        botãoSalvar.addActionListener(this);
        painelDeBotões.add(botãoSalvar);

        saldoLabel = new JLabel("Saldo: R$ " + saldo);
        painelDeBotões.add(saldoLabel);

        add(painelDeBotões, BorderLayout.WEST);

        painelDeJogadores = new JPanel();
        painelDeJogadores.setLayout(new BoxLayout(painelDeJogadores, BoxLayout.Y_AXIS));
        JScrollPane painelDeRolagem = new JScrollPane(painelDeJogadores);
        add(painelDeRolagem, BorderLayout.CENTER);

        jogadoresComprados = new ArrayList<>();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CartolaFC();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton botãoClicado = (JButton) e.getSource();
        String textoDoBotão = botãoClicado.getText();

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
                String nomeClube = dados[2];
                double preçoJogador = Double.parseDouble(dados[3]);
                String médiaJogador = dados[4];
                String últimaPontuaçãoJogador = dados[5];

                Clube clube = new Clube(nomeClube);
                Jogador jogadorDeFutebol;
                if (posiçãoJogador.equals("Treinador")) {
                    jogadorDeFutebol = new Treinador(nomeJogador, posiçãoJogador, clube, preçoJogador, médiaJogador, últimaPontuaçãoJogador);
                } else {
                    jogadorDeFutebol = new JogadorDeFutebol(nomeJogador, posiçãoJogador, clube, preçoJogador, médiaJogador, últimaPontuaçãoJogador);
                }

                JPanel painelDoJogador = new JPanel();
                painelDoJogador.setLayout(new BoxLayout(painelDoJogador, BoxLayout.Y_AXIS));
                painelDoJogador.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JLabel etiquetaNome = new JLabel("Nome: " + nomeJogador);
                JLabel etiquetaPosição = new JLabel("Posição: " + posiçãoJogador);
                JLabel etiquetaClube = new JLabel("Clube: " + nomeClube);
                JLabel etiquetaPreço = new JLabel("Preço: " + preçoJogador);
                JLabel etiquetaMédia = new JLabel("Média: " + médiaJogador);
                JLabel etiquetaÚltimaPontuação = new JLabel("Última Pontuação: " + últimaPontuaçãoJogador);

                JButton botãoComprar = new JButton("Comprar");
                botãoComprar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (saldo >= jogadorDeFutebol.preço) {
                            comprarJogador(jogadorDeFutebol);
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

    private List<String> filtrarJogadoresPorPosição(String posição) {
        List<String> jogadores = new ArrayList<>();

        if (posição.equalsIgnoreCase("Treinador")) {
            posição = "TECNICO";  
        }

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

    private void salvarJogadoresComprados() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("jogadoresComprados.csv"))) {
            for (Jogador jogador : jogadoresComprados) {
                String linha = jogador.nome + "," + jogador.posição + "," + jogador.clube.getNome() + "," + jogador.preço;
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void atualizarSaldo(double valor) {
        saldo += valor;
        saldoLabel.setText("Saldo: R$ " + String.format("%.2f", saldo));
    }

    private void comprarJogador(Jogador jogador) {
        jogadoresComprados.add(jogador);
        saldo -= jogador.preço;
        atualizarSaldo(0); // Atualiza o saldo exibido na interface gráfica
        JOptionPane.showMessageDialog(CartolaFC.this, "Jogador comprado com sucesso!");
    }
}
