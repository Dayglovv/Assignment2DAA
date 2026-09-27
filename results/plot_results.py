from pathlib import Path

import pandas as pd
import matplotlib.pyplot as plt


# ============================================================
# PATHS
# ============================================================

RESULTS_DIR = Path(__file__).resolve().parent
TABLES_DIR = RESULTS_DIR / "tables"


# ============================================================
# CHECK FILES
# ============================================================

files = {
    "workload1": TABLES_DIR / "workload1_random_access.csv",
    "workload2": TABLES_DIR / "workload2_search.csv",
    "workload3": TABLES_DIR / "workload3_insertion_removal.csv",
    "workload4": TABLES_DIR / "workload4_min_heap.csv"
}

for name, file in files.items():
    if not file.exists():
        print("ERROR: file not found:")
        print(file)
        exit()


# ============================================================
# READ DATA
# ============================================================

df1 = pd.read_csv(files["workload1"])
df2 = pd.read_csv(files["workload2"])
df3 = pd.read_csv(files["workload3"])
df4 = pd.read_csv(files["workload4"])


# ============================================================
# SHOW COLUMN NAMES
# ============================================================

print()
print("Workload 1 columns:")
print(df1.columns.tolist())

print()
print("Workload 2 columns:")
print(df2.columns.tolist())

print()
print("Workload 3 columns:")
print(df3.columns.tolist())

print()
print("Workload 4 columns:")
print(df4.columns.tolist())

print()


# ============================================================
# WORKLOAD 1
# RANDOM ACCESS
# ============================================================

plt.figure(figsize=(10, 6))

plt.plot(
    df1.iloc[:, 0],
    df1.iloc[:, 1],
    marker="o",
    label="DynamicArray"
)

plt.plot(
    df1.iloc[:, 0],
    df1.iloc[:, 2],
    marker="o",
    label="LinkedList"
)

plt.xscale("log")
plt.yscale("log")

plt.xlabel("Input size (n)")
plt.ylabel("Time (ns)")
plt.title("Workload 1: Random Access")

plt.legend()
plt.grid(True)

plt.tight_layout()

plt.savefig(
    RESULTS_DIR / "workload1_random_access.png",
    dpi=300
)

plt.close()


# ============================================================
# WORKLOAD 2
# SEARCH
# ============================================================

plt.figure(figsize=(10, 6))

plt.plot(
    df2.iloc[:, 0],
    df2.iloc[:, 1],
    marker="o",
    label="DynamicArray"
)

plt.plot(
    df2.iloc[:, 0],
    df2.iloc[:, 2],
    marker="o",
    label="LinkedList"
)

plt.xscale("log")
plt.yscale("log")

plt.xlabel("Input size (n)")
plt.ylabel("Time (ns)")
plt.title("Workload 2: Search")

plt.legend()
plt.grid(True)

plt.tight_layout()

plt.savefig(
    RESULTS_DIR / "workload2_search.png",
    dpi=300
)

plt.close()


# ============================================================
# WORKLOAD 3
# INSERTION / REMOVAL
# ============================================================

plt.figure(figsize=(12, 7))

# Columns 1-8 are assumed to be the 8 timing measurements
# after column 0 (n)

labels = [
    "DynamicArray Insert Begin",
    "DynamicArray Remove Begin",
    "DynamicArray Insert Middle",
    "DynamicArray Remove Middle",
    "LinkedList Insert Begin",
    "LinkedList Remove Begin",
    "LinkedList Insert Middle",
    "LinkedList Remove Middle"
]

for i in range(1, 9):
    if i < len(df3.columns):
        plt.plot(
            df3.iloc[:, 0],
            df3.iloc[:, i],
            marker="o",
            label=labels[i - 1]
        )

plt.xscale("log")
plt.yscale("log")

plt.xlabel("Input size (n)")
plt.ylabel("Time (ns)")
plt.title("Workload 3: Insertion and Removal")

plt.legend()
plt.grid(True)

plt.tight_layout()

plt.savefig(
    RESULTS_DIR / "workload3_insertion_removal.png",
    dpi=300
)

plt.close()


# ============================================================
# WORKLOAD 3
# OPERATIONS
# ============================================================

plt.figure(figsize=(12, 7))

# Find columns containing:
# Movement / Access information

for i, column in enumerate(df3.columns):
    column_lower = column.lower()

    if (
            "movement" in column_lower
            or "access" in column_lower
    ):
        plt.plot(
            df3.iloc[:, 0],
            df3.iloc[:, i],
            marker="o",
            label=column
        )

plt.xscale("log")
plt.yscale("log")

plt.xlabel("Input size (n)")
plt.ylabel("Operations")
plt.title("Workload 3: Movements and Node Accesses")

plt.legend()
plt.grid(True)

plt.tight_layout()

plt.savefig(
    RESULTS_DIR / "workload3_operations.png",
    dpi=300
)

plt.close()


# ============================================================
# WORKLOAD 4
# MIN-HEAP TIME
# ============================================================

plt.figure(figsize=(10, 6))

plt.plot(
    df4.iloc[:, 0],
    df4.iloc[:, 1],
    marker="o",
    label="Insert"
)

plt.plot(
    df4.iloc[:, 0],
    df4.iloc[:, 2],
    marker="o",
    label="ExtractMin"
)

plt.xscale("log")
plt.yscale("log")

plt.xlabel("Input size (n)")
plt.ylabel("Time (ns)")
plt.title("Workload 4: Min-Heap Performance")

plt.legend()
plt.grid(True)

plt.tight_layout()

plt.savefig(
    RESULTS_DIR / "workload4_min_heap_time.png",
    dpi=300
)

plt.close()


# ============================================================
# WORKLOAD 4
# COMPARISONS
# ============================================================

plt.figure(figsize=(10, 6))

plt.plot(
    df4.iloc[:, 0],
    df4.iloc[:, 3],
    marker="o",
    label="Insert Comparisons"
)

plt.plot(
    df4.iloc[:, 0],
    df4.iloc[:, 4],
    marker="o",
    label="ExtractMin Comparisons"
)

plt.xscale("log")
plt.yscale("log")

plt.xlabel("Input size (n)")
plt.ylabel("Number of comparisons")
plt.title("Workload 4: Min-Heap Comparisons")

plt.legend()
plt.grid(True)

plt.tight_layout()

plt.savefig(
    RESULTS_DIR / "workload4_min_heap_comparisons.png",
    dpi=300
)

plt.close()


# ============================================================
# FINISHED
# ============================================================

print("========================================")
print("All plots were created successfully!")
print("========================================")
print()
print("Plots saved to:")
print(RESULTS_DIR)