git pull
git add .

if [ ! $1 ]; then

  git commit -m "一点微小的工作 ~"
  
else

  git commit -m $1
  
fi

git push
