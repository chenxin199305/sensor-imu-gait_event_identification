package CNNY.Xin.model;

import java.util.ArrayList;

public class FootPressureForceSensorFrameDecoder {

	private FootPressureForceSensorDataDecoder dataDecoder;

	/**
	 *	Info:
	 *		Class Initiate 
	 */
	public FootPressureForceSensorFrameDecoder(FootPressureForceSensorDataDecoder dataDecoder) {
		this.dataDecoder = dataDecoder;
	}

	public boolean PacketDecode(ArrayList<Short> dataArray) {

		// 数据段长度
		int dataLength = 0;
		int checksum = 0;

		// 1. 至少应该存在 标志头(2) + 数据长度(1) + 结尾校验和(1)
		if (dataArray.size() < 4) {
			return false;
		}
		else {
			// 2. 遍历所有内容，寻找头
			for (int i = 0; i < (dataArray.size() - 3); i++) {

				// 2.1 找到了头标志
				/*
				 *	说明：	这里不能直接用 『 == 』 进行比较
				 *			java 里 == 比较的是对象的地址是否相等
				 *			java 里 equal 比较的是对象的值是否相等 
				 */
				if (dataArray.get(i).equals((short) 0xFF) && (dataArray.get(i + 1).equals((short) 0xF0))) {

					// 3. 获取数据段长度
					dataLength = dataArray.get(i + 2);

					// 4. 检查数据段长度是否够了, 如果长度不够, 说明读到了这一组的最新的数据了
					if ((dataArray.size() - (i + 3 + 1)) >= dataLength) {

						// 5. 比较校验和, 校验和是中间的数据的和值取后8位
						for (int j = 0; j < dataLength; j++) {
							checksum += dataArray.get(i + 3 + j);
						}
						checksum = checksum & 0xFF;

						if (checksum == dataArray.get(i + 2 + dataLength + 1)) {
						}
						else {
							// 如果校验和不对，跳过这组数据
							checksum = 0;
							continue;
						}

						// 6. 获取有效数据
						/*
						 *	说明：	
						 *			这里必须先验证校验和再获取数据，以保证数据的有效性!!! 
						 */
						ArrayList<Short> contentArray = new ArrayList<>();
						for (int j = 0; j < dataLength; j++) {
							contentArray.add(dataArray.get(i + 3 + j));
						}
						if (dataLength == 32) {
							dataDecoder.decode(contentArray);
						}
						else {
							System.err.println("FootPressureForceSensorFrameDecoder.PacketDecode() 接收到不合理的数据长度数据.");
							System.err.println("dataLength = " + dataLength);
						}
						
						// 7. 删除前面的所有的数据
						/*
						 *	说明：	
						 *			java里由于 remove 删除会自动将后面的元素往前移动，进行补位
						 *			所以只需要删第一个元素就可以了
						 */
						for (int j = 0; j < i + 3 + dataLength; j++) {
							dataArray.remove(0);
						}

						return true;
					}
					else {
						// 这里返回说明已经读到最新的数据了
						return false;
					}
				}
			}

			// 没有一组数据是正确的
			return false;
		}
	}

}
