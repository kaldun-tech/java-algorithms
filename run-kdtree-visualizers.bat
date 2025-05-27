@echo off
echo KdTree Visualizers Runner
echo ========================
echo.
echo 1. KdTree Visualizer (click to add points)
echo 2. Nearest Neighbor Visualizer
echo 3. Range Search Visualizer
echo.
set /p choice="Enter your choice (1-3): "

cd src\main\java\11_kd-trees

if "%choice%"=="1" (
  echo Running KdTree Visualizer...
  java -cp "..\..\..\..\lib\algs4.jar;." KdTreeVisualizer
) else if "%choice%"=="2" (
  echo Running Nearest Neighbor Visualizer...
  java -cp "..\..\..\..\lib\algs4.jar;." NearestNeighborVisualizer ..\..\resources\data\2dpoints.txt
) else if "%choice%"=="3" (
  echo Running Range Search Visualizer...
  java -cp "..\..\..\..\lib\algs4.jar;." RangeSearchVisualizer ..\..\resources\data\2dpoints.txt
) else (
  echo Invalid choice!
)

pause
