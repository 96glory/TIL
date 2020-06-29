# 02. 자연수의 합

```C++
#include <iostream>

using namespace std;

int main(){
	int n, m, sum = 0;
	cin >> n >> m;
	
	for(int i = n; i <= m; i++){
		cout << i;
		sum += i;
		if(i == m){
			cout << "=" << sum;
			break;
		}
		else		cout << "+";
		
	}
}
```
