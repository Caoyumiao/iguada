package life.iGuaDa.community.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomIdService {
    public String createRandomId() {
        Random random = new Random();
        int num = random.nextInt(9999999);
        String numString = String.valueOf(num);
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append(numString);
        for (int i = 0; i < 7 - numString.length(); i++) {
            stringBuffer.append("0");
        }
        return stringBuffer.toString();
    }

}
