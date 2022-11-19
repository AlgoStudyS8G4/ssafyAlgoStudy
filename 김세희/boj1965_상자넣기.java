package ag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class boj1965_상자넣기 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[] box = new int[N+1];
        int[] dp = new int[N+1];
        int max = 0; // 상자 개수 저장

        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=1;i<N+1;i++){
            box[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.fill(dp,1);
        for(int i=2;i<=N;i++){
            for(int j=i-1;j>=1;j--){
                if(box[i]>box[j]){
                    dp[i]=Math.max(dp[j]+1,dp[i]);
                }
            }
            max = Math.max(dp[i],max); // 최대값 갱신
        }

        System.out.println(max);


    }
}
