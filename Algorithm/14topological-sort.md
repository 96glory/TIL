# 위상 정렬 (Topological Sort)
* 어떤 일의 순서를 구하는 알고리즘
* 각각의 일의 선후관계가 주어지고, 선후관계를 유지하면서 전체 일의 순서를 짜는 알고리즘
* 그래프 적으로 재정의 : 방향 그래프에 존재하는 각 정점들의 선행 순서를 위배하지 않으면서 모든 정점을 나열하는 것
* 위상 정렬의 특징
  * 하나의 방향 그래프에는 여러 위상 정렬이 가능하다.
  * 위상 정렬의 과정에서 선택되는 정점의 순서를 위상 순서(Topological Order)라 한다.
* 조건
  * 위상 정렬의 과정에서 그래프에 남아 있는 정점 중에 진입 차수가 0인 정점이 없다면, 위상 정렬 알고리즘을 적용할 수 없다.

#### 문제
* 입력 예제 <br>
  ![image](https://user-images.githubusercontent.com/52440668/88374427-b57f4880-cdd4-11ea-8bbc-6bf3ea61ec64.png) <br>
  6 6 // 전체 일의 개수, 일의 순서 정보의 개수 <br>
  1 4 <br>
  5 4 <br>
  4 3 <br>
  2 5 <br>
  2 3 <br>
  6 2 <br>
  
* 출력 예제 <br>
  1 6 2 5 4 3

#### 해결
* 접근
  1. 각 정점들의 진입 차수를 기록
    * 진입 차수의 수 = 선행되어야 하는 작업의 수!
  2. 진입 차수가 0인 작업을 queue에 삽입
  3. queue의 front를 가지고 오고, pop (print), 그 front가 진입 차수에 영향을 준 작업의 진입 차수를 하나 뺀다.
  4. queue가 empty할 때까지 과정 2와 과정 3을 반복
* 과정
  ![그림2](https://user-images.githubusercontent.com/52440668/88387655-e3bd5200-cded-11ea-947a-c2f99fc687cc.png)
* 코드
```cpp
#include <bits/stdc++.h>
using namespace std;

int main(){
	ios_base::sync_with_stdio(false);
	
	int n, m, a, b, score;
	cin >> n >> m;
	vector< vector<int> > graph(n + 1, vector<int>(n + 1, 0));
	vector<int> degree(n + 1);
	queue<int> Q;
	
	for(int i = 0; i < m; i++){
		cin >> a >> b;
		graph[a][b] = 1;
		degree[b]++;
	}
	for(int i = 1; i <= n; i++){
		if(degree[i] == 0) Q.push(i);
	}
	
	while(!Q.empty()){
		int temp = Q.front();
		cout << temp << " ";
		Q.pop();
		
		for(int i = 1; i <= n; i++){
			if(graph[temp][i] == 1){
				degree[i]--;
				if(degree[i] == 0)
					Q.push(i);
			}
		}
	}
}

```
