package org.agingpuzzle.service;

import org.agingpuzzle.model.Update;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UpdatesService {

    public List<Update> getLastUpdates(int count) {
        Update update1 = new Update("MouseAge launches crowdsourced research", "mouse-age.png",
                        "A crowdfunded MouseAge launches crowdsourced research in deep learned biomarkers of aging",
                        "A crowdfunded MouseAge launches crowdsourced research in deep learned biomarkers of aging");

        Update update2 = new Update("Reqruitment for Study of UBX0101 is complete",
                "unity.png",
                "A Safety and Tolerability Study of UBX0101 in Patients With Osteoarthritis of the Knee: status was updated",
                "A Safety and Tolerability Study of UBX0101 in Patients With Osteoarthritis of the Knee: status was updated");

        return Arrays.asList(update1, update2, update1);
    }
}
