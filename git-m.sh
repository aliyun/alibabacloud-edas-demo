#!/bin/bash
#----changed the git history author info---
#---see http://baike.corp.taobao.com/index.php/Git-m --
bldgrn='\e[1;32m' # Green
bldylw='\e[1;33m' # Yellow
txtrst='\e[0m'    # Text Reset
function warning (){
        echo -n -e "${bldylw}WARNING:"
        echo -n -e "${txtrst}"
        echo $1
}
function info()
{
        echo -n -e "${bldgrn}INFO:"
        echo -n -e "${txtrst}"
        echo $1
}
force=1
if [ x"$1" = 'x-f' ];then
	force=0
fi
notfound=false
brs=$(git branch -l|awk '{print $NF}')
repo=$(git remote)
echo $repo |grep -q 'origin'
if [ $? = 1 ];then
	repo=$(git remote|head -1)
else
	repo='origin'
fi
i=0
while ! $notfound
do
  ref=$(git symbolic-ref HEAD)
  branch=${ref#refs/heads/}
  notremote="false"
  git branch -r |grep -q "$repo/$branch$"
  if [ $? = 1 ];then # not found the remote branch
	echo "Info:$branch is a new local branch, I will fix all commmit"
	area="$branch"
	notremote="true"
  else
	area="$repo/$branch..$branch"
  fi
  email=`git log $area --pretty=%aE|grep -E -v "@alibaba-inc.com|@taobao.com|@tmall.com|@alipay.com|@aliyun-inc.com"|head -1`
  if [ x"$email" = 'x' ];then
	# seeking other branches
	for branch in $brs
	do
		email=`git log $area --pretty=%aE|grep -E -v "@alibaba-inc.com|@taobao.com|@tmall.com|@alipay.com|@aliyun-inc.com"|head -1`
		if [ ! x"$email" = 'x' ];then
			break
		fi
	done
	if [  x"$email" = 'x' ];then
		notfound=true
		if [ $i -gt 0 ];then
			break
		fi
	fi
  fi
  let i=i+1
  echo -n "Input the wrong email that you want to fix($email)?"
  while read mail;do
	if [ ! "$mail" = "" ];then
		email=$mail
	fi
	break
  done

  echo -n "Input the correct username($name): "
  while read name1;do
     if [ ! "$name1" = "" ];then
	  name=$name1
     fi
     if [  "$name" = "" ];then
	  echo -n "cann't empty. please input your name:"
     else
	  break
     fi
  done
  echo -n "Input the correct email($newemail): "
  while read newemail1;do
     if [ ! "$newemail1" = "" ];then
		  newemail=$newemail1
     fi
     if [  "$newemail" = "" ];then
		echo -n "cann't empty. please input your new email:"
     else
		break
     fi
     echo $newemail|grep -q '@' || {
		  echo -n "please input the correct email:"
		  continue
     }
  done
  for branch in $brs
  do
#ref=$(git symbolic-ref HEAD)
#branch=${ref#refs/heads/}
      if [ $notremote = "true" ];then
          begin=`git log $area --pretty=%h|tail -2|head -1`
      else
          begin=`git log $area --pretty=%h|tail -1`
      fi
      if [ x"$begin" != "x" ];then
      	 filename=xuejiang.sh
      	 info "I will changed $email to $name <$newemail>"
         echo "git filter-branch  -f --commit-filter '" > $filename
         echo "        if [ \"\$GIT_AUTHOR_EMAIL\" = \"$email\" ];" >> $filename
         echo "       then
                GIT_AUTHOR_NAME=\"$name\";
                GIT_AUTHOR_EMAIL=\"$newemail\";
                git commit-tree \"\$@\";
        else
                git commit-tree \"\$@\"; " >> $filename
        if [ $notremote = "true" ];then
          echo "fi' -- $branch
                  " >> $filename
        else
          echo "fi' -- ${begin}^..$branch $branch
                  " >> $filename
        fi
        sh $filename	
    		if [ $? = 0 ];then
    			info "$email changed to $newemail in $branch. You can press ctrl+C to exit git-m"
    		fi
    		rm $filename
      fi
   done
done
if [ $force = 1 ];then
	 info "modify finished. You can try 'git push' now"
	 exit;
fi
all=`git log --author=$email --pretty=%h`
echo "I will changed $email to $name <$newemail>"
info "found the history commits by author $email:"
echo $all
info " you should fix the commits  that not git push yet."
warning "don't change the history that have been pushed to server. see 'git log' first "
warning "It seems not be the local commits. Be careful to type 'yes'"
for sha in $all
do
	echo -n "Should do I  change the author info of the commit: $sha ? yes/no: "
	while read answer;do
		if [ "$answer" != 'yes' ];then
			echo "$sha skipped"
			break;
		fi
		echo "To modify $sha now"
		filename=xuejiang.sh
		echo "git filter-branch  -f --commit-filter '" > $filename
		echo "        if [ \"\$GIT_AUTHOR_EMAIL\" = \"$email\" ];" >> $filename
		echo "       then
                GIT_AUTHOR_NAME=\"$name\";
                GIT_AUTHOR_EMAIL=\"$newemail\";
                git commit-tree \"\$@\";
        else
                git commit-tree \"\$@\";
        fi' -- ${sha}^..${sha} HEAD 
		" >> $filename
		sh $filename
		break
	done
done
rm $filename
info "modify finished. You can try 'git push' now"
