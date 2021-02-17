import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;

import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

public class VitaliySwiper extends JFrame {
    private Game game;
    private JPanel panel;
    private JLabel label;
    public final int COLS = 9;
    public final int ROWS = 9;
    public final int BOMBS = 10;
    public final int IMAGE_SIZE = 50;


    public static void main(String[] args) {
        new VitaliySwiper();
    }


    private VitaliySwiper() {
        game = new Game(COLS, ROWS, BOMBS);
        game.start();
        setImages();
        initLable();
        initPanel();
        initFrame();

    }

    private void initPanel() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords()) {
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
                }

            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x, y);

                if (e.getButton() == MouseEvent.BUTTON1) {
                    game.pressLeftButton(coord);
                    label.setText(getGameStatus());
                    panel.repaint();
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    game.pressRihtButton(coord);
                    panel.repaint();
                } else  if (e.getButton() == MouseEvent.BUTTON2){
                    game.start();
                    panel.repaint();
                }
            }
        });

        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x * IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE));
        add(panel);
    }

    private String getGameStatus() {
        switch (game.getState()) {
            case PLAYED:
                return "play";
            case BOOMBED:
                return "BIG BADA BUM";
            case WINER:
                return "YOU WIN!";
            default:
                return "Welcom";
        }

    }

    private void initLable() {
        label = new JLabel("Welcom");
        add(label, BorderLayout.SOUTH);
    }

    private void initFrame() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Vitaly Sweeper");
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setIconImage(getImage("icon"));
    }

    private void setImages() {
        for (Box box : Box.values()) {
            box.image = getImage(box.name().toLowerCase());
        }
    }

    private Image getImage(String name) {

        String fileName = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(fileName));
        return icon.getImage();
    }

}
