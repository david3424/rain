package com.noah.crm.cloud.common.event;

import com.noah.crm.cloud.common.test.callbacks.AskTestEventFirstCallback;
import com.noah.crm.cloud.common.test.callbacks.AskTestEventSecondCallback;
import com.noah.crm.cloud.common.test.callbacks.UnitedTestEventCallback;
import com.noah.crm.cloud.common.test.handlers.AskTestEventHandler;
import com.noah.crm.cloud.common.test.handlers.NotifyFirstTestEventFirstHandler;
import com.noah.crm.cloud.common.test.handlers.NotifyFirstTestEventSecondHandler;
import com.noah.crm.cloud.common.test.handlers.RevokableAskTestEventHandler;

/**
 */
public class EventTestUtils {

    public static void clear() {
        AskTestEventFirstCallback.successParams.clear();
        AskTestEventSecondCallback.successParams.clear();
        AskTestEventSecondCallback.failureParams.clear();
        UnitedTestEventCallback.successParams.clear();
        UnitedTestEventCallback.failureParams.clear();
        AskTestEventHandler.events.clear();
        NotifyFirstTestEventFirstHandler.events.clear();
        NotifyFirstTestEventSecondHandler.events.clear();
        RevokableAskTestEventHandler.events.clear();
        RevokableAskTestEventHandler.revokeEvents.clear();

    }

}
