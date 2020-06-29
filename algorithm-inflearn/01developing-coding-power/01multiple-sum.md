# 01. 1부터 N까지의 M의 배수합

```C++
#include <iostream>

using namespace std;

int main(void){
	int N, M, sum;
	cin >> N >> M;
	
	for(int i = 1; i <= N; i++){
		if(i % M == 0){
			sum += i;
		}
	}
	
	cout << sum;
}
```
