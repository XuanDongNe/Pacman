package game.model.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Utils {
	private static Map<Integer, Double> directionConverterMap = new HashMap<>();

	static {
		directionConverterMap.put(0, 0d);
		directionConverterMap.put(1, Math.PI);
		directionConverterMap.put(2, Math.PI / 2);
		directionConverterMap.put(3, Math.PI * (3 / 2));
	}

	// Phương thức để lấy khoảng cách giữa hai điểm
	public static double getDistance(double xA, double yA, double xB, double yB) {
		return Math.sqrt(Math.pow(xB - xA, 2) + Math.pow(yB - yA, 2));
	}

	// Phương thức để lấy góc tạo bởi hai điểm
	public static double getDirection(double xA, double yA, double xB, double yB) {
		return Math.atan2((yB - yA), (xB - xA));
	}

	// Phương thức để lấy điểm từ một điểm ban đầu, một góc và một khoảng cách
	public static int[] getPointDistanceDirection(int x, int y, double distance, double direction) {
		int[] point = new int[2];
		point[0] = x + (int) (Math.cos(direction) * distance);
		point[1] = y + (int) (Math.sin(direction) * distance);
		return point;
	}

	// Phương thức để chuyển đổi một "hướng" của một thực thể thành một góc tính
	// bằng radians thông qua bản đồ được tạo ra ở trên
	public static double directionConverter(int spriteDirection) {
		return directionConverterMap.get(spriteDirection);
	}

	// Phương thức để tạo một số nguyên từ 0 đến n
	public static int randomInt(int n) {
		Random r = new Random();
		return r.nextInt(n);
	}

	// Phương thức để tạo một số nguyên từ min đến max bao gồm cả min và max
	public static int randomInt(int min, int max) {
		Random r = new Random();
		return r.nextInt(max - min + 1) + min;
	}

	// Phương thức để tạo một giá trị boolean ngẫu nhiên
	public static boolean randomBool() {
		return (randomInt(2) == 1);
	}
}