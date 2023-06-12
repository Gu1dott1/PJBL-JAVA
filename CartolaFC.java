import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

abstract class Player {
    String name;
    String position;
    String club;
    String price;
    String playerAverage;
    String playerLastScore;

    Player(String name, String position, String club, String price, String playerAverage, String playerLastScore) {
        this.name = name;
        this.position = position;
        this.club = club;
        this.price = price;
        this.playerAverage = playerAverage;
        this.playerLastScore = playerLastScore;
    }

    abstract void playerInfo();
}

class SoccerPlayer extends Player {
    SoccerPlayer(String name, String position, String club, String price, String playerAverage, String playerLastScore) {
        super(name, position, club, price, playerAverage, playerLastScore);
    }

    @Override
    void playerInfo() {
        System.out.println("Player Info:");
    }
}

class CustomException extends Exception {
    CustomException(String message) {
        super(message);
    }
}

public class CartolaFC extends JFrame implements ActionListener {
    private List<JButton> positionButtons;
    private JPanel playersPanel;
    private List<Player> jogadoresComprados;

    public CartolaFC() {
        setTitle("Cartola FC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        positionButtons = new ArrayList<>();
        positionButtons.add(new JButton("Goleiro"));
        positionButtons.add(new JButton("Zagueiro 1"));
        positionButtons.add(new JButton("Zagueiro 2"));
        positionButtons.add(new JButton("Lateral 1"));
        positionButtons.add(new JButton("Lateral 2"));
        positionButtons.add(new JButton("Meia 1"));
        positionButtons.add(new JButton("Meia 2"));
        positionButtons.add(new JButton("Meia 3"));
        positionButtons.add(new JButton("Atacante 1"));
        positionButtons.add(new JButton("Atacante 2"));
        positionButtons.add(new JButton("Atacante 3"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        for (JButton button : positionButtons) {
            button.addActionListener(this);
            button.setPreferredSize(new Dimension(100, 30));
            buttonPanel.add(button);
        }
        
        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(this);
        buttonPanel.add(saveButton);
        
        add(buttonPanel, BorderLayout.WEST);

        playersPanel = new JPanel();
        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(playersPanel);
        add(scrollPane, BorderLayout.CENTER);

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
        JButton clickedButton = (JButton) e.getSource();
        String buttonText = clickedButton.getText();

        if (buttonText.equals("Salvar")) {
            saveJogadoresComprados();
        } else {
            String position = buttonText.split(" ")[0];
            List<String> players = filterPlayersByPosition(position);

            playersPanel.removeAll();
            for (String player : players) {
                String[] data = player.split(",");
                String playerName = data[0];
                String playerPosition = data[1];
                String playerClub = data[2];
                String playerPrice = data[3];
                String playerAverage = data[4];
                String playerLastScore = data[5];

                Player soccerPlayer = new SoccerPlayer(playerName, playerPosition, playerClub, playerPrice, playerAverage, playerLastScore);
                JPanel playerPanel = new JPanel();
                playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
                playerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JLabel nameLabel = new JLabel("Nome: " + playerName);
                JLabel positionLabel = new JLabel("Posição: " + playerPosition);
                JLabel clubLabel = new JLabel("Clube: " + playerClub);
                JLabel priceLabel = new JLabel("Preço: " + playerPrice);
                JLabel averageLabel = new JLabel("Média: " + playerAverage);
                JLabel lastScoreLabel = new JLabel("Última Pontuação: " + playerLastScore);

                JButton buyButton = new JButton("Comprar");
                buyButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        jogadoresComprados.add(soccerPlayer);
                        JOptionPane.showMessageDialog(CartolaFC.this, "Jogador comprado: " + playerName);
                        clickedButton.setEnabled(false);
                    }
                });

                playerPanel.add(nameLabel);
                playerPanel.add(positionLabel);
                playerPanel.add(clubLabel);
                playerPanel.add(priceLabel);
                playerPanel.add(averageLabel);
                playerPanel.add(lastScoreLabel);
                playerPanel.add(buyButton);

                playersPanel.add(playerPanel);
            }

            playersPanel.revalidate();
            playersPanel.repaint();
        }
    }

    private List<String> filterPlayersByPosition(String position) {
        List<String> players = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("jogadores.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6 && data[1].equalsIgnoreCase(position)) {
                    players.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return players;
    }

    private void saveJogadoresComprados() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("jogadoresComprados.csv"))) {
            for (Player jogador : jogadoresComprados) {
                String line = jogador.name + "," + jogador.position + "," + jogador.club + "," + jogador.price + "," + jogador.playerAverage + "," + jogador.playerLastScore;
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
