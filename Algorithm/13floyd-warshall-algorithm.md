# 플로이드-와샬 알고리즘 (Floyd-Warshall Algorithm)
* 그래프에서 모든 정점에서 모든 정점으로의 최단 경로를 구하는 알고리즘

* 접근
  * dis[i][j]
    * 초기값 : 정점 i에서 정점 j로 이동할 때, __한 간선__ 만 사용해 이동하는 비용을 기록한 배열
      ![그림1](https://user-images.githubusercontent.com/52440668/88369672-7e586980-cdcb-11ea-8f7e-8cd5b54e89b2.png)
    * 값이 결정되는 시기
      * 정점 i에서 정점 j로 이동할 때, __여러 간선__ 을 거쳐 이동하는 비용 중 최소 비용이 도출되었을 때
  * 플로이드-와샬 알고리즘의 점화식
    * 정점이 추가될 때마다 최단 경로를 relax한다.
    * 정점 k가 추가될 때, (정점 k가 추가되기 전 relax했던 정점 i에서 정점 j로 이동하는 비용)과 (정점 i에서 정점 k의 최소 비용 + 정점 k에서 정점 j로의 최소 비용)을 비교하여 더 작은 값으로 relax한다.
      ```cpp
      // 기존의 i -> j의 값이 더 저렴한가, i -> k -> j의 값이 더 저렴한가?
      dis[i][j] = min(dis[i][j], dis[i][k] + dis[k][j]);
      ```

* 코드
```cpp
#include <bits/stdc++.h>
#define MAX_COST 1000000000
using namespace std;

int a, b, c, n, m, arr[101][101], dp[101][101];

int main(){
	ios_base::sync_with_stdio(false);
	cin >> n >> m;
	for(int i = 1; i <= m; i++){
		cin >> a >> b >> c;
		arr[a][b] = dp[a][b] = c;
	}
	for(int i = 1; i <= n; i++){
		for(int j = 1; j <= n; j++){
			if(dp[i][j] == 0 && i != j)
				dp[i][j] = MAX_COST;
		}
	}
	
	for(int k = 1; k <= n; k++){
		for(int i = 1; i <= n; i++){
			for(int j = 1; j <= n; j++){
				if(k == i || k == j || i == j) continue;
				dp[i][j] = min(dp[i][j], dp[i][k] + dp[k][j]);
			}
		}
	}
	
	for(int i = 1; i <= n; i++){
		for(int j = 1; j <= n; j++){
			if(dp[i][j] == MAX_COST) cout << "M ";
			else cout << dp[i][j] << " ";
		}
		cout << endl;
	}
}
```
