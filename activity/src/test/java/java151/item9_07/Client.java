package java151.item9_07;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Client {
	public static void main(String[] args) throws Exception {
		//����һ�����̵߳��첽ִ����
		ExecutorService es = Executors.newSingleThreadExecutor();
		//�߳�ִ�к������ֵ
		Future<Integer> future = es.submit(new TaxCalculator(100));
		while(!future.isDone()){
			//��û��������ɣ��ȴ�10����
			TimeUnit.MILLISECONDS.sleep(2000);
			//������ȷ���
			System.out.print("#");
		}
		System.out.printf("%n������ɣ�˰���ǣ�%d Ԫ", future.get());
		//�ر��첽ִ����
		es.shutdown();
	}
}

//˰�������
class TaxCalculator implements Callable<Integer> {
	//����
	private int seedMoney;
	//�������߳��ṩ�Ĳ���
	public TaxCalculator(int _seedMoney) {
		seedMoney = _seedMoney;
	}
	@Override
	public Integer call() throws Exception {
		//���Ӽ��㣬����һ����Ҫ10��
		TimeUnit.MILLISECONDS.sleep(10000);
		return seedMoney /10;
	}
}