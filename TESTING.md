# Testing OAuth on Heroku

## Quick Answer: Yes, it will work!

Your `application.properties` already uses environment variables:
- `${GITHUB_CLIENT_ID:your-github-client-id}` - reads from env var or uses default
- `${GITHUB_CLIENT_SECRET:your-github-client-secret}` - reads from env var or uses default
- Same for Google OAuth

## Step 1: Test Locally First (Recommended)

### Test with Environment Variables Locally

1. **Set environment variables** (choose one method):

   **Option A: Export in terminal** (Linux/Mac):
   ```bash
   export GITHUB_CLIENT_ID=your-actual-github-client-id
   export GITHUB_CLIENT_SECRET=your-actual-github-client-secret
   export GOOGLE_CLIENT_ID=your-actual-google-client-id
   export GOOGLE_CLIENT_SECRET=your-actual-google-client-secret
   ./gradlew bootRun
   ```

   **Option B: Create `.env` file** (not tracked in git):
   ```bash
   # Create .env file in project root
   GITHUB_CLIENT_ID=your-actual-github-client-id
   GITHUB_CLIENT_SECRET=your-actual-github-client-secret
   GOOGLE_CLIENT_ID=your-actual-google-client-id
   GOOGLE_CLIENT_SECRET=your-actual-google-client-secret
   ```

   Then run: `source .env && ./gradlew bootRun`

   **Option C: Use IntelliJ/VS Code run configuration**:
   - Set environment variables in your IDE's run configuration

2. **Update OAuth Apps with Local Redirect URI**:
   - **GitHub**: Settings → Developer settings → OAuth Apps → Your App
     - Add `http://localhost:8080/login/oauth2/code/github` to Callback URL
   - **Google**: Google Cloud Console → APIs & Services → Credentials → OAuth 2.0 Client
     - Add `http://localhost:8080/login/oauth2/code/google` to Authorized redirect URIs

3. **Test OAuth Login**:
   ```bash
   # Start your app
   ./gradlew bootRun
   
   # Visit in browser:
   # http://localhost:8080/oauth2/authorization/github
   # or
   # http://localhost:8080/oauth2/authorization/google
   
   # After login, check:
   # http://localhost:8080/api/users/me
   ```

## Step 2: Deploy to Heroku

### 1. Create Heroku App (if not done):
```bash
heroku create your-app-name
# Note your app URL: https://your-app-name.herokuapp.com
```

### 2. Set Heroku Config Vars:
```bash
# Required OAuth credentials
heroku config:set GITHUB_CLIENT_ID=your-github-client-id
heroku config:set GITHUB_CLIENT_SECRET=your-github-client-secret
heroku config:set GOOGLE_CLIENT_ID=your-google-client-id
heroku config:set GOOGLE_CLIENT_SECRET=your-google-client-secret

# Optional: Database (if using Supabase, you may need these too)
# heroku config:set DB_USERNAME=postgres
# heroku config:set DB_PASSWORD=your-password

# Verify config vars
heroku config
```

### 3. Update OAuth Apps with Heroku Redirect URI:
   - **GitHub**: 
     - Callback URL: `https://your-app-name.herokuapp.com/login/oauth2/code/github`
   - **Google**: 
     - Authorized redirect URI: `https://your-app-name.herokuapp.com/login/oauth2/code/google`

### 4. Deploy:
```bash
# Make sure Procfile exists and build.gradle is correct
git add Procfile
git commit -m "Add Procfile for Heroku"
git push heroku main
```

### 5. Test on Heroku:
```bash
# Check logs
heroku logs --tail

# Test OAuth endpoints:
# Visit in browser:
# https://your-app-name.herokuapp.com/oauth2/authorization/github
# or
# https://your-app-name.herokuapp.com/oauth2/authorization/google

# After login, check user info:
# https://your-app-name.herokuapp.com/api/users/me
```

## Step 3: Verify It's Working

### Check OAuth Configuration:
```bash
# On Heroku, check logs for OAuth errors:
heroku logs --tail | grep -i oauth

# Test endpoints:
curl https://your-app-name.herokuapp.com/api/users/me
# Should return 401 if not authenticated, or user info if logged in
```

### Test Flow:
1. Visit: `https://your-app-name.herokuapp.com/oauth2/authorization/github`
2. You'll be redirected to GitHub to authorize
3. After authorization, you'll be redirected back to `/api/users/me`
4. Should see JSON with your user info (userId, username, email, provider)

## Common Issues & Solutions

### Issue: "redirect_uri_mismatch" error
**Solution**: Make sure the redirect URI in GitHub/Google OAuth apps exactly matches:
- For Heroku: `https://your-app-name.herokuapp.com/login/oauth2/code/github`
- For local: `http://localhost:8080/login/oauth2/code/github`

### Issue: "invalid_client" error
**Solution**: Check that config vars are set correctly:
```bash
heroku config:get GITHUB_CLIENT_ID
heroku config:get GITHUB_CLIENT_SECRET
```

### Issue: Application won't start on Heroku
**Solution**: Check build logs:
```bash
heroku logs --tail
# Look for errors during build or startup
```

### Issue: Database connection fails
**Solution**: Make sure database URL/credentials are set:
```bash
heroku config:get DATABASE_URL
# Or if using Supabase, set DB_USERNAME and DB_PASSWORD
```

## Quick Test Commands

```bash
# View all config vars
heroku config

# View specific var
heroku config:get GITHUB_CLIENT_ID

# Restart app (to pick up new config vars)
heroku restart

# Open app in browser
heroku open

# Test specific endpoint
curl https://your-app-name.herokuapp.com/api/users/me
```

