import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartolaFC extends JFrame implements ActionListener {
    private List<JButton> positionButtons;
    private JTextArea outputTextArea;

    public CartolaFC() {
        setTitle("Cartola FC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Criando os botões de posição
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

        // Configurando o painel superior com os botões de posição
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        for (JButton button : positionButtons) {
            button.addActionListener(this);
            buttonPanel.add(button);
        }
        add(buttonPanel, BorderLayout.WEST);

        // Configurando a área de texto para exibir os jogadores
        outputTextArea = new JTextArea();
        add(new JScrollPane(outputTextArea), BorderLayout.CENTER);

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
        String position = clickedButton.getText();

        // Ler o arquivo CSV e filtrar jogadores pela posição
        List<String> players = filterPlayersByPosition(position);

        // Exibir os jogadores na área de texto
        outputTextArea.setText("");
        for (String player : players) {
            String[] data = player.split(",");
            String playerName = data[0];
            String playerPosition = data[1];
            String playerClub = data[2];
            String playerPrice = data[3];
            String playerAverage = data[5];
            String playerLastScore = data[6];

            String playerInfo = "Nome: " + playerName + "\n" +
                    "Posição: " + playerPosition + "\n" +
                    "Clube: " + playerClub + "\n" +
                    "Preço: " + playerPrice + "\n" +
                    "Média: " + playerAverage + "\n" +
                    "Última Pontuação: " + playerLastScore + "\n\n";

            outputTextArea.append(playerInfo);
        }
    }

}
