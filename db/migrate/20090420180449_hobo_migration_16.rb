class HoboMigration16 < ActiveRecord::Migration
  def self.up
    drop_table :node_memberships
    drop_table :teams
    drop_table :team_memberships
    drop_table :team_node_memberships
    drop_table :variants
  end

  def self.down
    create_table "node_memberships", :force => true do |t|
      t.datetime "created_at"
      t.datetime "updated_at"
      t.integer  "node_id"
      t.integer  "user_id"
    end
    
    create_table "teams", :force => true do |t|
      t.string   "name"
      t.integer  "owner_id"
      t.datetime "created_at"
      t.datetime "updated_at"
    end
    
    create_table "team_memberships", :force => true do |t|
      t.datetime "created_at"
      t.datetime "updated_at"
      t.integer  "team_id"
      t.integer  "user_id"
      t.integer  "role_id"
    end
    
    create_table "team_node_memberships", :force => true do |t|
      t.datetime "created_at"
      t.datetime "updated_at"
      t.integer  "team_id"
      t.integer  "node_id"
    end
    
    create_table "variants", :force => true do |t|
      t.string   "name"
      t.integer  "node_id"
      t.integer  "variant_node_id"
      t.datetime "created_at"
      t.datetime "updated_at"
    end
  end
end
