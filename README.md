# Word Embedding Similarity Search Engine

---

## Overview
This project contains an interactive command-line Java application designed to parse high-dimensional word embedding files and execute vector similarity searches using mathematical distance calculations. Developed as a core project for the **Object Orientated Software Design** module of the Higher Diploma in Software Development curriculum, the application maps raw word tokens to their corresponding vector coordinate arrays to establish semantic relationships. The codebase demonstrates object-oriented design principles, low-level array structures, custom file parsing, and vector calculations, structured cleanly to showcase technical maintainability to technical recruiters.

---

## Features
- Benchmark semantic word connections using custom mathematical Vector Norm, Dot Product, and Cosine Similarity calculations.
- Parse multi-dimensional coordinate dictionaries into separate data fields using lightweight string isolation arrays.
- Filter, isolate, and rank the top 10 closest contextual word matches matching a target metric threshold above 0.6.
- Zero external package dependencies, leveraging an isolated ANSI colour-mapping scheme for complete terminal cross-portability.

---

## Project Structure
The repository is organised into a clean, modular package structure to isolate the interactive command-line interface from the underlying mathematical processing and data streaming logic:

```text
Word-Embedding-Similarity-Search/
└── src/
    └── ie/
        └── atu/
            └── sw/
                ├── ConsoleColour.java   # Custom ANSI escape configurations for terminal UI styling
                ├── MenuHandler.java     # Interactive CLI manager, input filters, and runtime loops
                ├── Runner.java          # Main application engine and execution entry point
                └── SearchFeatures.java  # Core vector mathematics, similarity algorithms, and file I/O
```

---

## Installation

1. Clone the repository:
```bash
git clone https://github.com/SamK-ie/Word-Embedding-Similarity-Search.git
```
2. Navigate to the source root directory:
```bash
cd Word-Embedding-Similarity-Search/src
```
3. Compile the source code via the CLI:
```bash
javac -d . *.java
```

---

## Execution and Usage

### Word Embedding Vector Search Engine

1. Run the compiled bytecode application engine to launch the interactive interface:
```bash
java ie.atu.sw.Runner
```
2. Follow the numbered menu paths to supply embedding coordinates and target inputs.

### Expected Interface Output Example

```plaintext
************************************************************
*     ATU - Dept. of Computer Science & Applied Physics    *
*                                                          *
*          Similarity Search with Word Embeddings          *
*                                                          *
************************************************************
(1) Specify Embedding File
(2) Specify an Output File (default: ./out.txt)
(3) Enter a Word or Text
(4) Run Search
(5) Quit

Please Select an Option: 
4
Search complete. Please open the ./out.txt to view the results.
Results written to the output file successfully.
```
### Core Mathematical Formulations Reference

| Metric Component | Formula Type | Functional Implementation Objective |
| :--- | :--- | :--- |
| **Vector Norm** | Euclidean Length | Calculates vector magnitude: $\|v\| = \sqrt{\sum v_i^2}$ |
| **Dot Product** | Scalar Vector Sum | Sums directional coordinate pairs: $A \cdot B = \sum A_i B_i$ |
| **Cosine Similarity** | Angular Distance | Evaluates geometric closeness: $\text{Sim}(A,B) = \frac{A \cdot B}{\|A\| \|B\|}$ |

---

## Production Hardening Roadmap
To demonstrate how this localised command-line tool scales toward enterprise-ready software pipelines, the following architectural milestones are planned:

- **Optimised Memory Footprint:** Shifting from standard multidimensional arrays to dynamic primitive-backed buffers to eliminate structural garbage collection overhead during dense dataset processing.
- **Multithreaded Vector Scaling:** Distributing the calculation loops across a parallel processing pipeline or ForkJoinPool to evaluate high-dimensional dictionary files across multiple CPU cores simultaneously.
- **Decoupled Configuration Payloads:** Abstracting static matching thresholds and baseline IO configurations into explicit data storage platforms that dynamically ingest raw API payloads, ensuring the underlying core engine remains entirely decoupled and software-agnostic.

---

## License
This project is open-source software distributed under the terms of the MIT License.

---

## Contributing
Contributions are welcome!

If you have suggestions for structural improvements, data parsing additions, or extra automation features, please fork the repository and submit a pull request.

---

## Authors
- [@SamK-ie](https://github.com/SamK-ie)

   
