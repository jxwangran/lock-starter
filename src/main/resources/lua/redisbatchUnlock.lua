for i,v in ipairs(KEYS) do
	if redis.call('get', KEYS[1]) == ARGV[1] 
		then return redis.call('del', v) 
		return 1
	else 
		return 0 
	end
end
return 1