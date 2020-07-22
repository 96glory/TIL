# 동적 계획법 (DP : Dynamic Programming)
* 쉽게 구할 수 있는 작은 문제의 답으로부터 큰 문제의 답을 이끌어내는 방식
* 보통 점화식을 세워서 큰 문제의 답을 해결한다.
* 문제 : n 미터의 줄을 1 미터와 2 미터 단위로 자르는 가짓수 출력

## Bottom-Up
* 가장 작은 문제들로부터 답을 구해가며 전체 문제의 답을 찾는 방식
* 반목문을 이용해서 구현
```cpp
#include <bits/stdc++.h>
using namespace std;

int n, answer[46];

int main(){
	scanf("%d", &n);
	
	answer[i] = 1;
	answer[i] = 2;
	for(int i = 3; i <= n; i++)
		answer[i] = answer[i - 1] + answer[i - 2];
	printf("%d", answer[n]);
}
```

## Top-Down
* 가장 큰 문제를 방문 후 작은 문제를 호출하여 답을 찾는 방식
* 재귀와 메모이제이션을 이용해서 구현
```cpp
#include <bits/stdc++.h>
using namespace std;

int n, answer[46];

int dfs(int n){
	if(answer[n] > 0) return answer[n];
	if(n == 1 || n == 2) return n;
	else return answer[n] = dfs(n - 1) + dfs(n - 2);
}

int main(){
	scanf("%d", &n);
	printf("%d", dfs(n));
}
```
