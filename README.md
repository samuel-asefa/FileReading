# File Reading in Java

Six Java file-reading exercises from Data Structures coursework at South Brunswick High School, with sample inputs and saved reference outputs.

## Included

| Program | Concept | Default input |
| --- | --- | --- |
| `AverageFromFile` | Numeric parsing, running sum, and skipped invalid lines | `data/fileio/numbers.txt` |
| `LucasNumbers` | Cached recurrence with BigInteger | `data/fileio/lucas.txt` |
| `PlausiblePolygon` | Delimited parsing and four plausibility conditions | `data/fileio/polygons.txt` |
| `TicTacTally` | Board simulation and winning-line checks | `data/fileio/TicTac.txt` |
| `TemperatureReport` | Sliding window with earliest-tie handling | `data/fileio/temperatures.txt` |
| `RangeTotals` | Prefix sums for inclusive range queries | `data/fileio/rangeTotals.txt` |

## Compile and run

Requires **JDK 17 or newer**. Run commands from this repository's root. No external dependencies or sibling repositories are needed.

```sh
javac --release 17 -d build "@sources.txt"
java -cp build AverageFromFile
java -cp build LucasNumbers
java -cp build PlausiblePolygon
java -cp build TicTacTally
java -cp build TemperatureReport
java -cp build RangeTotals
java -cp build AverageFromFile data/fileio/nums.txt
```

`sources.txt` lists only the working source files in `src/`. Classes retain the default package used in class.

## Notes

All six programs use BufferedReader/FileReader with try-with-resources. Their first command-line argument can override the input filename.

- Lucas input: one nonnegative index per line; L(0) = 2 and L(1) = 1.
- Polygon input: comma-separated sides, a pipe, then comma-separated angles.
- Tic-tac-toe input: the starting player X/O, followed by positions 1 through 9.
- Temperature input: a positive window size, followed by integer temperatures; at least one full window is required.
- Range-total input: N, N values, Q, then Q inclusive zero-based `start,end` queries.

The five later assignment programs assume correctly formatted worksheet inputs. Reference outputs for those programs are in `expected-output/fileio/`. The earlier average exercise reports malformed numeric lines to stderr and skips them.

## Checks

```sh
bash scripts/check.sh
```

Or compile and run the same topic checks manually:

```sh
javac --release 17 -d build "@sources.txt"
javac --release 17 -cp build -d build tests/FileReadingChecks.java
java -cp build FileReadingChecks
```

## Original code

`archive/originals/` preserves the recovered topic-specific originals, including earlier starters where applicable. Those snapshots are excluded from compilation. See [source notes](docs/SOURCE_NOTES.md) for working-copy changes.

Samuel Asefa · Data Structures · 2026–2027
