package org.openxava.ex.datasource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.openxava.ex.datasource.MonitoredDataSourceFactory.ConnectionWrapper;

/**
 * Trace the open and closing of a connection which managed by {@link MonitoredDataSourceFactory}, see {@link ConnectionWrapper} for more information.
 */
public class ConnectionTrace {
	private static Map<String, TraceInfo> traceInfoMem = new ConcurrentHashMap<String, ConnectionTrace.TraceInfo>();
	
	public static void attachTraceInfo(TraceInfo info){
		traceInfoMem.put(info.id, info);
	}
	public static void detachTraceInfo(TraceInfo info){
		traceInfoMem.remove(info.id);
	}
	public static List<TraceInfo> dumpTraceInfoList(){
		List<TraceInfo> infos = new ArrayList<TraceInfo>();
		infos.addAll(traceInfoMem.values());
		Collections.sort(infos, new Comparator<TraceInfo>() {
			public int compare(TraceInfo o1, TraceInfo o2) {
				long t1 = o1.createTime;
				long t2 = o2.createTime;
				if (t1>t2){
					return 1;
				}else if (t1<t2){
					return 2;
				}else{
					return 0;
				}
			}
		});
		return infos;
	}
	
	public static class TraceInfo implements Serializable{
		private static final long serialVersionUID = 20131102L;
		/** Unique identifier of trace info */
		private String id;
		/** Connection create time, in milliseconds */
		private Long createTime;
		/** The stackTrace where connection created */
		private StackTraceElement[] stackTrace;
		/** Other information, maybe the SQL logging */
		private LinkedHashMap<String, String> moreInfo;
		
		public TraceInfo(StackTraceElement[] stackTrace){
			this.id = UUID.randomUUID().toString();
			this.createTime = System.currentTimeMillis();
			this.stackTrace = stackTrace;
			this.moreInfo = new LinkedHashMap<String, String>();
		}
		
		public String getId() {
			return id;
		}
		public Long getCreateTime() {
			return createTime;
		}
		public StackTraceElement[] getStackTrace() {
			return stackTrace;
		}
		public LinkedHashMap<String, String> getMoreInfo() {
			return new LinkedHashMap<String, String>(moreInfo);
		}
		
		public void putInfo(String key, String info){
			this.moreInfo.put(key, info);
		}
	}
}
