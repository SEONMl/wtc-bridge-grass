package bridge.domain;

import bridge.dto.BridgeDto;

import java.util.*;
import java.util.List;

/**
 * 다리 건너기 게임을 관리하는 클래스
 */
public class BridgeGame {
    private final Bridge bridge;
    private final List<String> myBridge;

    public BridgeGame(Bridge bridge) {
        this.bridge = bridge;
        this.myBridge = new ArrayList<>();
    }

    /**
     * 사용자가 칸을 이동할 때 사용하는 메서드
     * <p>
     * 이동을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public MatchResult move(String input) {
        myBridge.add(input);
        return match();
    }

    private MatchResult match() {
        int bound = myBridge.size() - 1;
        if (bridge.matchWithIndex(bound, myBridge.get(bound))) {
            return isLastMatch(bound);
        }
        return MatchResult.FAILURE;
    }

    private MatchResult isLastMatch(int idx) {
        if (bridge.isFinish(idx)) {
            return MatchResult.FINISH;
        }
        return MatchResult.SUCCESS;
    }


    /**
     * 사용자가 게임을 다시 시도할 때 사용하는 메서드
     * <p>
     * 재시작을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void retry() {
        myBridge.clear();
    }

    public BridgeDto getMyBridgeToPrint(MatchResult recentResult) {
        BridgeDto bridgeDto = new Bridge(myBridge).toPrint();
        if (recentResult.equals(MatchResult.FAILURE)) {
            bridgeDto.swap(myBridge.get(myBridge.size() - 1));
        }
        return bridgeDto;
    }
}
