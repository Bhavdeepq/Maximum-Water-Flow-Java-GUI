# GUI-Based Maximum Water Flow Solver (Ford-Fulkerson)

[cite_start]This repository contains a **Java Swing-based GUI application** that visualizes the **Ford-Fulkerson Algorithm** (using the **Edmonds-Karp** variation) to solve the Maximum Water Flow problem in an urban distribution network[cite: 13, 17, 60].

[cite_start]The project models a system where water travels from a central reservoir (**Source**) to a treatment plant (**Sink**) through various regional stations and pipelines[cite: 18].

---

## 🚀 Key Features

* [cite_start]**Interactive Visualization:** Renders regional stations as nodes and pipelines as connected lines using Java's `Graphics2D`[cite: 20, 279].
* [cite_start]**Step-by-Step Execution:** Users can find augmenting paths one at a time using the "Find Next Augmenting Path" button to trace the routing logic[cite: 21, 64].
* [cite_start]**Dynamic Path Highlighting:** Automatically highlights the current augmenting path in **red** with a thicker stroke during each step of execution[cite: 21, 33, 265].
* [cite_start]**Real-Time Data Display:** Shows active flow versus maximum capacity (e.g., "10 / 20 MLD") for each pipeline directly on the visual canvas[cite: 217, 266].
* [cite_start]**Live Status Log:** A built-in text log updates with the current total flow and the specific nodes involved in the most recent path found[cite: 34, 177].

---

## 🧠 The Algorithm: Edmonds-Karp

[cite_start]The system implements the Edmonds-Karp variation of the Ford-Fulkerson method, which guarantees an optimal execution time by always selecting the shortest available path via **Breadth-First Search (BFS)**[cite: 60, 61].

### The Core Logic
1.  [cite_start]**Residual Network:** Tracks available capacity using a 2D adjacency matrix[cite: 31, 62].
2.  [cite_start]**Augmenting Paths:** A BFS traverses from the Source to the Sink, examining only pipelines with remaining unused capacity[cite: 65].
3.  [cite_start]**Bottleneck Identification:** The algorithm checks the remaining capacity of every pipeline in the path to find the absolute minimum value[cite: 69, 70].
4.  [cite_start]**Flow Augmentation:** It adds the bottleneck value to the forward flow and subtracts it from the reverse flow, allowing the algorithm to undo sub-optimal routing choices in future iterations[cite: 72, 73, 74].

---

## 🛠️ Tech Stack

* [cite_start]**Language:** Java (JDK) [cite: 278, 280]
* [cite_start]**GUI Framework:** Java Swing (JFrame, JPanel, JButton, JLabel) [cite: 17, 279, 282]
* [cite_start]**Graphics:** `Graphics2D` for custom edge and node rendering [cite: 279, 282]
* [cite_start]**Data Structures:** 2D Adjacency Matrices for capacity and flow tracking [cite: 31, 78]

---

## 📖 How to Use

1.  [cite_start]**Launch the App:** Upon starting, the application displays a hardcoded 8-node urban network[cite: 52, 239].
2.  [cite_start]**Find Paths:** Click **"Find Next Augmenting Path"** to trigger the BFS search[cite: 64, 113].
3.  [cite_start]**Observe Updates:** The GUI will highlight the found path and update the flow values across the network[cite: 178, 265].
4.  [cite_start]**Termination:** Once no more paths exist, the status label will display: **"MAXIMUM FLOW REACHED!"**[cite: 67, 151].

---

## 📥 Installation & Execution

```bash
# 1. Clone the repository
git clone [https://github.com/yourusername/water-flow-max-flow.git](https://github.com/yourusername/water-flow-max-flow.git)

# 2. Navigate to the source folder
cd water-flow-max-flow

# 3. Compile the Java source code
javac WaterDistributionSystem.java

# 4. Run the application
java WaterDistributionSystem
