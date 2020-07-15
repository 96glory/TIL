# 깊이 우선 탐색 (DFS : depth-first search)
* 트리나 그래프에서 한 루트로 탐색하다가 특정 상황에서 최대한 깊숙히 들어가서 확인한 뒤, 더 들어갈 수 없다면 최근에 확인한 노드로 돌아가 다른 루트로 탐색하는 방식
* 전위순회 (preorder)
  * 자기 자신 -> 왼쪽 노드 -> 오른쪽 노드 순서로 탐색
* 중위순회 (inorder)
  * 왼쪽 노드 -> 자기 자신 -> 오른쪽 노드 순서로 탐색
* 후위순회 (postorder)
  * 왼쪽 노드 -> 오른쪽 노드 -> 자기 자신 순서로 탐색
```cpp
#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

void preorder(int v){
	if(v > 7)	return;
	else{
		printf("%d ", v); // 자기 자신
		preorder(v * 2); // 왼쪽 자식 노드 
		preorder(v * 2 + 1); // 오른쪽 자식 노드
	}
}

void inorder(int v){
	if(v > 7)	return;
	else{
		inorder(v * 2); // 왼쪽 자식 노드 
		printf("%d ", v); // 자기 자신
		inorder(v * 2 + 1); // 오른쪽 자식 노드
	}
}

void postorder(int v){
	if(v > 7)	return;
	else{
		postorder(v * 2); // 왼쪽 자식 노드 
		postorder(v * 2 + 1); // 오른쪽 자식 노드
		printf("%d ", v); // 자기 자신
	}
}

int main(){
	preorder(1);	printf("\n");
	inorder(1);	printf("\n");
	postorder(1);	printf("\n");
}
```
