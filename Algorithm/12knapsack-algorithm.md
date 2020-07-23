# 냅색 알고리즘 (Knapsack Algorithm)
* 가방에 담을 수 있는 무게의 한계값과 보석의 종류가 주어진다.
  보석의 무게와 가치가 주어진다.
  보석은 종류별로 무한하게 있다고 가정하자.
  가방에 보석을 담을 때, 최대의 가치를 출력하시오.
* 입력 예제
  4 11
  5 12 
  3 8
  6 14
  4 8
* 출력 예제
  28

* 접근 및 해결 포인트
  * dp[index]에 무엇을 저장할까?
    * 가방에 __index만큼의 무게__ 까지 담을 수 있을 때의 보석 초대 가치를 저장
  * 보석을 기준으로 for문이 도는데, 그 보석이 dp에 영향을 줄수 있을 때가 index >= 보석.weight이다.
    그 index부터 dp[index - 보석.weight] + 보석.value를 도출해낼 수 있다. (기존의 dp[index])와 (dp[index - 보석.weight] + 보석.value)를 비교하여 큰 값으로 dp[index]를 업데이트한다.
    단, 보석을 추가하려고 할 때 가방의 최대 무게를 고려하여야 한다.
* 접근 과정
<br>
![그림1](https://user-images.githubusercontent.com/52440668/88272849-4e01c400-cd14-11ea-95b9-075a3e3d8d54.png)

* 코드
```cpp
#include <bits/stdc++.h>
using namespace std;

int n, m, w, v;

int main(){
	ios_base::sync_with_stdio(false);
	
	cin >> n >> m;
	vector<int> dp(m + 1, 0);
	
	for(int i = 0; i < n; i++){
		cin >> w >> v;
		for(int j = w; j < m; j++){
			dp[j] = max(dp[j], dp[j - w] + v);
		}
	}
	
	cout << dp[m];
}
```
