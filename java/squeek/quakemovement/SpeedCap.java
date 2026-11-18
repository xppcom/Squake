package squeek.quakemovement;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public final class SpeedCap
{
	public static final double TICKS_PER_SECOND = 20.0D;

	public static double capPerTick()
	{
		return ModConfig.MAX_SPEED_BLOCKS_PER_SECOND / TICKS_PER_SECOND;
	}

	public static double toBlocksPerSecond(double blocksPerTick)
	{
		return blocksPerTick * TICKS_PER_SECOND;
	}

	public static boolean clampMotion(EntityPlayer player)
	{
		if (player == null || capPerTick() <= 0.0D)
			return false;

		double[] clamped = clampVelocity(player.motionX, player.motionY, player.motionZ);

		if (clamped[0] == player.motionX && clamped[1] == player.motionY && clamped[2] == player.motionZ)
			return false;

		player.motionX = clamped[0];
		player.motionY = clamped[1];
		player.motionZ = clamped[2];

		return true;
	}

	public static double[] clampVelocity(double x, double y, double z)
	{
		double cap = capPerTick();

		if (cap <= 0.0D)
			return new double[]{x, y, z};

		double horizontal = MathHelper.sqrt_double(x * x + z * z);
		if (horizontal > cap)
		{
			double multi = cap / horizontal;
			x *= multi;
			z *= multi;
		}

		double total = MathHelper.sqrt_double(x * x + y * y + z * z);
		if (total > cap)
		{
			double multi = cap / total;
			x *= multi;
			y *= multi;
			z *= multi;
		}

		return new double[]{x, y, z};
	}
}
