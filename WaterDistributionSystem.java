import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class WaterDistributionSystem extends JFrame {

    private static final int V = 8; 
    private static final int SOURCE = 0; 
    private static final int SINK = 7;   
    private int[][] capacity;
    private int[][] flow;
    private int totalFlow = 0;
    
    private final String[] nodeNames = {"S", "C1", "C2", "C3", "C4", "C5", "C6", "T"};
    private final Point[] nodePos = {
        new Point(50, 200), new Point(200, 100), new Point(200, 300), 
        new Point(350, 50), new Point(350, 200), new Point(350, 350), 
        new Point(500, 100), new Point(650, 200)
    };

    private List<Integer> currentPath = null;
    private GraphPanel graphPanel;
    private JLabel infoLabel;
    private JButton nextStepBtn;

    public WaterDistributionSystem() {
        setTitle("Water Distribution Max Flow - Ford Fulkerson (Edmonds-Karp)");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initGraph();
        graphPanel = new GraphPanel();
        graphPanel.setBackground(Color.WHITE);
        add(graphPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        nextStepBtn = new JButton("Find Next Augmenting Path");
        infoLabel = new JLabel("Total Flow: 0 MLD | Ready");
        infoLabel.setFont(new Font("Monospaced", Font.BOLD, 14));

        nextStepBtn.addActionListener(e -> executeNextStep());

        bottomPanel.add(nextStepBtn);
        bottomPanel.add(Box.createHorizontalStrut(20));
        bottomPanel.add(infoLabel);
        add(bottomPanel, BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
    }

    private void initGraph() {
        capacity = new int[V][V];
        flow = new int[V][V];
        capacity[0][1] = 20; capacity[0][2] = 15;
        capacity[1][3] = 10; capacity[1][4] = 10;
        capacity[2][4] = 5;  capacity[2][5] = 10;
        capacity[3][6] = 10; capacity[4][6] = 10;
        capacity[5][7] = 10; capacity[6][7] = 20;
    }

    private void executeNextStep() {
        int[] parent = new int[V];
        Arrays.fill(parent, -1);
        Queue<Integer> queue = new LinkedList<>();
        queue.add(SOURCE);
        parent[SOURCE] = SOURCE;
        
        boolean foundPath = false;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v = 0; v < V; v++) {
                // Check residual capacity
                if (parent[v] == -1 && capacity[u][v] - flow[u][v] > 0) {
                    parent[v] = u;
                    queue.add(v);
                    if (v == SINK) {
                        foundPath = true;
                        break;
                    }
                }
            }
            if (foundPath) break;
        }

        if (!foundPath) {
            infoLabel.setText("Total Flow: " + totalFlow + " MLD | MAXIMUM FLOW REACHED!");
            infoLabel.setForeground(new Color(0, 128, 0));
            currentPath = null;
            nextStepBtn.setEnabled(false);
            graphPanel.repaint();
            return;
        }

        // Calculate bottleneck capacity
        int pathFlow = Integer.MAX_VALUE;
        for (int v = SINK; v != SOURCE; v = parent[v]) {
            int u = parent[v];
            pathFlow = Math.min(pathFlow, capacity[u][v] - flow[u][v]);
        }

        // Update flow and track current path for visualization
        currentPath = new ArrayList<>();
        currentPath.add(SINK);
        for (int v = SINK; v != SOURCE; v = parent[v]) {
            int u = parent[v];
            flow[u][v] += pathFlow;
            flow[v][u] -= pathFlow; 
            currentPath.add(u);
        }
        Collections.reverse(currentPath);
        totalFlow += pathFlow;

        StringBuilder pathStr = new StringBuilder("Path: ");
        for (int i = 0; i < currentPath.size(); i++) {
            pathStr.append(nodeNames[currentPath.get(i)]);
            if (i < currentPath.size() - 1) pathStr.append("->");
        }
        
        infoLabel.setText(String.format("Total Flow: %d MLD | %s (+%d)", totalFlow, pathStr.toString(), pathFlow));
        graphPanel.repaint();
    }

    private class GraphPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw all edges
            for (int u = 0; u < V; u++) {
                for (int v = 0; v < V; v++) {
                    if (capacity[u][v] > 0) drawEdge(g2d, u, v);
                }
            }

            // Draw all nodes
            for (int i = 0; i < V; i++) {
                int x = nodePos[i].x;
                int y = nodePos[i].y;
                g2d.setColor(new Color(173, 216, 230));
                g2d.fillOval(x - 20, y - 20, 40, 40);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(x - 20, y - 20, 40, 40);
                g2d.setFont(new Font("Arial", Font.BOLD, 14));
                FontMetrics fm = g2d.getFontMetrics();
                g2d.drawString(nodeNames[i], x - fm.stringWidth(nodeNames[i]) / 2, y + fm.getAscent() / 2 - 2);
            }
        }

        private void drawEdge(Graphics2D g2d, int u, int v) {
            boolean isHighlighted = false;
            if (currentPath != null) {
                for (int i = 0; i < currentPath.size() - 1; i++) {
                    if (currentPath.get(i) == u && currentPath.get(i+1) == v) {
                        isHighlighted = true; 
                        break;
                    }
                }
            }

            g2d.setColor(isHighlighted ? Color.RED : Color.LIGHT_GRAY);
            g2d.setStroke(new BasicStroke(isHighlighted ? 3 : 1));
            g2d.drawLine(nodePos[u].x, nodePos[u].y, nodePos[v].x, nodePos[v].y);

            // Label for Flow/Capacity
            int midX = (nodePos[u].x + nodePos[v].x) / 2;
            int midY = (nodePos[u].y + nodePos[v].y) / 2;
            
            String label = flow[u][v] + "/" + capacity[u][v];
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            FontMetrics fm = g2d.getFontMetrics();
            
            // Draw background for label clarity
            g2d.setColor(new Color(255, 255, 255, 220));
            g2d.fillRect(midX - fm.stringWidth(label)/2 - 2, midY - fm.getAscent(), fm.stringWidth(label) + 4, fm.getHeight());
            
            g2d.setColor(isHighlighted ? Color.RED : new Color(0, 102, 204));
            g2d.drawString(label, midX - fm.stringWidth(label)/2, midY);
        }
    }

    public static void main(String[] args) {
        // Corrected the lambda arrow here
        SwingUtilities.invokeLater(() -> new WaterDistributionSystem().setVisible(true));
    }
}