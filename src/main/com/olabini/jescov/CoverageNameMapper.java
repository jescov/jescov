package com.olabini.jescov;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CoverageNameMapper {
    private final AtomicInteger id = new AtomicInteger(0);
    private final ConcurrentMap<Integer, String> idToNameMap = new ConcurrentHashMap<Integer, String>();
    private final ConcurrentMap<String, Integer> nameToIdMap = new ConcurrentHashMap<String, Integer>();

    public Integer map(String filePath) {
      if (nameToIdMap.containsKey(filePath)) {
        return nameToIdMap.get(filePath);
      }
      Integer pathId = id.getAndIncrement();
      idToNameMap.put(pathId, filePath);
      nameToIdMap.put(filePath, pathId);
      return pathId;
    }

    public String unmap(Integer fileId) {
      return idToNameMap.get(fileId);
    }
}
