import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CartolaFC extends JFrame {
    private List<JButton> positionButtons;
    private JPanel playersPanel;
    private List<String> boughtPlayers;

    public CartolaFC() {
        setTitle("Cartola FC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        
        boughtPlayers = new ArrayList<>();

        
        positionButtons = new ArrayList<>();
        positionButtons.add(new JButton("Goleiro"));
        positionButtons.add(new JButton("Zagueiro"));
        positionButtons.add(new JButton("Zagueiro"));
        positionButtons.add(new JButton("Lateral"));
        positionButtons.add(new JButton("Lateral"));
        positionButtons.add(new JButton("Meia"));
        positionButtons.add(new JButton("Meia"));
        positionButtons.add(new JButton("Meia"));
        positionButtons.add(new JButton("Atacante"));
        positionButtons.add(new JButton("Atacante"));
        positionButtons.add(new JButton("Atacante"));
        

        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        for (JButton button : positionButtons) {
            button.addActionListener(this::handlePositionButton);
            buttonPanel.add(button);
        }
        add(buttonPanel, BorderLayout.WEST);

        
        playersPanel = new JPanel();
        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(playersPanel), BorderLayout.CENTER);

        setVisible(true);
    }

    private void handlePositionButton(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        String position = clickedButton.getText();

        
        playersPanel.removeAll();

        
        List<String> players = filterPlayersByPosition(position);

        
        for (String player : players) {
            String[] data = player.split(",");
            String playerName = data[0];
            String playerPosition = data[1];
            String playerClub = data[2];
            String playerPrice = data[3];
            String playerAverage = data[5];
            String playerLastScore = data[6];

            String playerInfo = "Nome: " + playerName +
                    ", Posição: " + playerPosition +
                    ", Clube: " + playerClub +
                    ", Preço: " + playerPrice +
                    ", Média: " + playerAverage +
                    ", Última Pontuação: " + playerLastScore;

            JButton buyButton = new JButton("Comprar " + playerName);
            buyButton.addActionListener(actionEvent -> showPlayerInfo(playerInfo));

            JPanel playerPanel = new JPanel();
            playerPanel.add(new JLabel(playerInfo));
            playerPanel.add(buyButton);

            playersPanel.add(playerPanel);
        }

        
        playersPanel.revalidate();
        playersPanel.repaint();
    }

    private void showPlayerInfo(String playerInfo) {
        JDialog dialog = new JDialog(this, "Informações do Jogador", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(300, 200);

        JTextArea infoArea = new JTextArea(playerInfo);
        infoArea.setEditable(false);
        dialog.add(new JScrollPane(infoArea), BorderLayout.CENTER);

        JButton buyButton = new JButton("Comprar");
        buyButton.addActionListener(actionEvent -> {
            boughtPlayers.add(playerInfo);
            saveBoughtPlayers();
            dialog.dispose();
        });
        dialog.add(buyButton, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private List<String> filterPlayersByPosition(String position) {
        List<String> players = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("jogadoresEditados.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 2 && data[1].equalsIgnoreCase(position)) {
                    players.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return players;
    }

    
    private void saveBoughtPlayers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("jogadoresComprados.csv"))) {
            for (String player : boughtPlayers) {
                writer.write(player + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CartolaFC();
            }
        });
    }
}
