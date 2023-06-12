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

class JogadorDeFutebol extends Jogador {
    JogadorDeFutebol(String nome, String posição, String clube, double preço, String médiaDoJogador, String últimaPontuação) {
        super(nome, posição, clube, preço, médiaDoJogador, últimaPontuação);
    }

    @Override
    void informaçõesDoJogador() {
        System.out.println("Informações do Jogador:");
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
