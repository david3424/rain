package java151.item9_06;

/**
 * ���volatile���ܱ�֤�̰߳�ȫ��ֻ�ܱ�֤��ǰ�̻߳�ȡ��ֵ������
 */
public class Client {
	public static void main(String[] args) throws Exception {
		// ����ֵ������Ϊ���ѭ������
		int value = 1000;
		// ѭ����������ֹ��������ѭ������������
		int loops = 0;
		//���߳��飬���ڹ��ƻ�߳���
		ThreadGroup tg = Thread.currentThread().getThreadGroup();
		while (loops++ < value) {
			// ������Դ����
			UnsafeThread ut = new UnsafeThread();
			System.out.println("1��ǰ����߳�������" + tg.activeCount());
			for (int i = 0; i < value; i++) {
				new Thread(ut).start();
				System.out.println("2��ǰ����߳�������" + tg.activeCount());
			}
			System.out.println("3��ǰ����߳�������" + tg.activeCount());
			// �ȵ�15���룬�ȴ���߳�������Ϊ1
			do {
				Thread.sleep(15);
			} while (tg.activeCount() != 1);
			// ���ʵ��ֵ������ֵ�Ƿ�һ��
			if (ut.getCount() != value) {
				// �����̲߳���ȫ�����
				System.out.println("ѭ������ " + loops + " �飬�����̲߳���ȫ���");
				System.out.println("��ʱ��count=" + ut.getCount());
				System.exit(0);
			}
		}
	}
}

class UnsafeThread implements Runnable {
	// ������Դ
	private volatile int count = 0;

	@Override
	public void run() {
		// Ϊ������CPU�ķ�æ�̶�
		for (int i = 0; i < 1000; i++) {
			Math.hypot(Math.pow(92456789, i), Math.cos(i));
		}
		// ��������
		count++;
	}

	public int getCount() {
		return count;
	}
}
