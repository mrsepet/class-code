#William Emmanuel
#wre9fz
#Apr 15, 2013
#Makefile

CXX=g++ $(CXXFLAGS)
CXXFLAGS=-O2
OFILES = heapTest.o heap.o huffNode.o huffmanenc.o
heapTest: $(OFILES)
	$(CXX) $(OFILES)
	@echo "Compile sucessful"
	
heapTest.o: heapTest.cpp
	$(CXX) -O2 -c heapTest.cpp
	
heap.o: heap.cpp heap.h
	$(CXX) -O2 -c heap.cpp
	
huffNode.o: huffNode.cpp huffNode.h
	$(CXX) -O2 -c huffNode.cpp
	
huffmanenc.o: huffmanenc.cpp huffmanenc.h
	$(CXX) -O2 -c huffmanenc.cpp
	
	
clean:
	-rm *.o wordPuzzle
