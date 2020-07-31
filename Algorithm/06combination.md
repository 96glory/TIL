# 조합 (combination)
* 문제 : 0으로 시작하는 숫자 n개 중 r개를 조합으로 뽑는 방법
## DFS를 이용하는 방법
![그림2](https://user-images.githubusercontent.com/52440668/88025704-9df35600-cb6f-11ea-9d9d-c298e111ff7e.png)
```cpp
#include <bits/stdc++.h>
using namespace std;

int n, r, ch[20];

void dfs(int s, int L){
	if(L == r){
		for(int i = 0; i < r; i++)
			printf("%d ", ch[i]);
		printf("\n");
	}
	else{
		for(int i = s; i < n; i++){
			ch[L] = i;
			dfs(i + 1, L + 1);
		}
	}
}

int main(){
	cin >> n >> r;
	dfs(0, 0);
}
```

## C++ STL 를 사용하는 방법
```cpp
#include <bits/stdc++.h>
using namespace std;

int n, r;

int main (){
	cin >> n >> r;
	vector<int> v, ind;
	for(int i = 0; i < n ; i++)
		v.push_back(i);
	for(int i = 0; i < r; i++)
		ind.push_back(0); // 1
	for(int i = 0; i < n - r; i++)
		ind.push_back(1); // 0
	sort(ind.begin(), ind.end());
	
	do{
		for(int i = 0; i < n; i++){
			if(ind[i] == 0) // 1
				printf("%d ", v[i]);
		}
		printf("\n");
	}while(next_permutation(ind.begin(), ind.end()));
}
```
 
