# GUI-Based Maximum Water Flow Solver (Ford-Fulkerson)

This repository contains a Java Swing-based GUI application that visualizes the Ford-Fulkerson Algorithm (using the Edmonds-Karp variation) to solve the Maximum Water Flow problem in an urban distribution network. 

The project models a system where water travels from a central reservoir (Source) to a treatment plant (Sink) through various regional stations and pipelines.

---

## 🚀 Key Features

* **Interactive Visualization:** Renders regional stations as nodes and pipelines as connected lines using Java's `Graphics2D`.
* **Step-by-Step Execution:** Users can find augmenting paths one at a time using the "Find Next Augmenting Path" button to trace the routing logic.
* **Dynamic Path Highlighting:** Automatically highlights the current augmenting path in **red** with a thicker stroke during each step of execution.
* **Real-Time Data Display:** Shows active flow versus maximum capacity (e.g., "10 / 20 MLD") for each pipeline directly on the visual canvas.
* **Live Status Log:** A built-in text log updates with the current total flow and the specific nodes involved in the most recent path found.

---

## 🧠 The Algorithm: Edmonds-Karp

The system implements the Edmonds-Karp variation of the Ford-Fulkerson method, which guarantees an optimal execution time by always selecting the shortest available path via **Breadth-First Search (BFS)**.



### The Core Logic
1.  **Residual Network:** Tracks available capacity using a 2D adjacency matrix.
2.  **Augmenting Paths:** A BFS traverses from the Source to the Sink, examining only pipelines with remaining unused capacity.
3.  **Bottleneck Identification:** The algorithm checks the remaining capacity of every pipeline in the path to find the absolute minimum value.
4.  **Flow Augmentation:** It adds the bottleneck value to the forward flow and subtracts it from the reverse flow, allowing the algorithm to undo sub-optimal routing choices in future iterations.

---

## 🛠️ Tech Stack

* **Language:** Java (JDK)
* **GUI Framework:** Java Swing (JFrame, JPanel, JButton, JLabel)
* **Graphics:** `Graphics2D` for custom edge and node rendering
* **Data Structures:** 2D Adjacency Matrices for capacity and flow tracking

---

## 📖 How to Use

1.  **Launch the App:** Upon starting, the application displays a hardcoded 8-node urban network.
2.  **Find Paths:** Click **"Find Next Augmenting Path"** to trigger the BFS search.
3.  **Observe Updates:** The GUI will highlight the found path and update the flow values across the network.
4.  **Termination:** Once no more paths exist, the status label will display: **"MAXIMUM FLOW REACHED!"**.

---

## 📥 Installation & Execution

```bash
# 1. Clone the repository
git clone [https://github.com/Bhavdeepq/Maximum-Water-Flow-Java-GUI]

# 2. Navigate to the source folder
cd water-flow-max-flow

# 3. Compile the Java source code
javac WaterDistributionSystem.java

# 4. Run the application
java WaterDistributionSystem
