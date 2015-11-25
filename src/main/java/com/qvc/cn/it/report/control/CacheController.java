package com.qvc.cn.it.report.control;

import java.util.Collection;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;

import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CacheController extends BaseController {
	
	@Resource
	EhCacheCacheManager cacheManager;
	
	@ResponseBody
	@RequestMapping(value = "/cache", method = RequestMethod.GET)
	public Collection<String> getCaches() {
		return cacheManager.getCacheNames();
	}
	
	@ResponseBody
	@RequestMapping(value = "/cache/{name}", method = RequestMethod.GET)	
	public String getCache(@PathVariable String name) {
		Cache cache = cacheManager.getCacheManager().getCache(name);
		return formatCache(cache);
	}
	
	private String formatCache(Cache cache) {
		StringBuilder sb = new StringBuilder();
		sb.append("Total " + Integer.toString(cache.getSize()) + " records in this cache.");
		return sb.toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/cache/{name}", method = RequestMethod.DELETE)
	public void evictCache (@PathVariable String name) {
		cacheManager.getCacheManager().getCache(name).removeAll();
	}
}
